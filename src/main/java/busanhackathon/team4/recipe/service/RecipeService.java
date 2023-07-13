package busanhackathon.team4.recipe.service;

import busanhackathon.team4.exception.CustomException;
import busanhackathon.team4.food.dto.FoodDto;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import busanhackathon.team4.recipe.dto.RecipeDto;
import busanhackathon.team4.recipe.entity.Recipe;
import busanhackathon.team4.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;

    /**
     * api 에서 받아온 레시피 저장
     */
    public Long saveRecipe(String username, RecipeDto recipeDto) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        Recipe recipe = Recipe.builder()
                .foodName(recipeDto.getFoodName())
                .method(recipeDto.getMethod())
                .isPublic(false)
                .member(member)
                .build();
        recipeRepository.save(recipe);
        log.info("레시피 저장 완료");
        return recipe.getId();
    }

    public void removeRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
        log.info("레시피 삭제 완료");
    }

    /**
     * 레시피 전체 조회
     */
    @Transactional(readOnly = true)
    public List<RecipeDto> findAllRecipeByMember(String username) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        List<RecipeDto> recipeDtoList = recipeRepository.findAllRecipeByMember(member.getId()).stream()
                .map(recipe -> RecipeDto.builder()
                        .recipeId(recipe.getId())
                        .foodName(recipe.getFoodName())
                        .method(recipe.getMethod())
                        .isPublic(recipe.getIsPublic())
                        .build())
                .collect(Collectors.toList());
        return recipeDtoList;
    }

    /**
     * 공개 & 비공개 설정
     */
    public void changePublic(String username, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(recipeDto.getRecipeId())
                .orElseThrow(() -> new EntityNotFoundException("없는 레시피입니다."));
        if(!recipe.getMember().getLoginId().equals(username)) {
            throw new CustomException("본인이 작성할 글만 권한 공개 설정 가능합니다.");
        }
        // 요청온 값으로 update
        recipe.setIsPublic(recipeDto.getIsPublic());
    }

    /**
     * 레시피 상세 보기
     */
    public RecipeDto findOneById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("없는 레시피입니다."));
        RecipeDto recipeDto = RecipeDto.builder()
                .foodName(recipe.getFoodName())
                .method(recipe.getMethod())
                .isPublic(recipe.getIsPublic())
                .build();
        return recipeDto;
    }
}
