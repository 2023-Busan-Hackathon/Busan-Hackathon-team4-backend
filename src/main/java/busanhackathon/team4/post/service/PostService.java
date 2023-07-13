package busanhackathon.team4.post.service;

import busanhackathon.team4.exception.CustomException;
import busanhackathon.team4.heart.entity.Heart;
import busanhackathon.team4.heart.repository.HeartRepository;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import busanhackathon.team4.post.dto.PostDto;
import busanhackathon.team4.post.entity.Post;
import busanhackathon.team4.post.repository.PostRepository;
import busanhackathon.team4.recipe.entity.Recipe;
import busanhackathon.team4.recipe.repository.RecipeRepository;
import busanhackathon.team4.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final HeartRepository heartRepository;
    private final RecipeService recipeService;
    private final RecipeRepository recipeRepository;

    public Long enrollPost(String username, PostDto postDto) {
        // 해당 레시피로 등록한 게시글이 있는지 체크
        Optional<Post> existPost = postRepository.findByRecipeId(postDto.getRecipeId());
        if (existPost.isPresent()) {
            throw new CustomException("현재 등록하고자 하는 레시피로 이미 등록된 게시글이 있습니다.");
        }

        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("회원이 없습니다."));

        Recipe recipe = recipeRepository.findById(postDto.getRecipeId())
                .orElseThrow(() -> new EntityNotFoundException("없는 레시피입니다."));

        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .member(member)
                .recipe(recipe)
                .build();

        //게시글 저장
        postRepository.save(post);

        // 게시글 등록 했으면 해당 recipe public 으로 권한 변경
        recipeService.changePublic(username, post.getRecipe().getId());
        return post.getId();
    }

    public List<PostDto> findAllPost(String username) {
        // 회원이 누른 heart 조회
        List<Heart> heartList = heartRepository.findByMemberId(username);
        log.info("회원이 누른 heart 조회");
        // 회원이 좋아요 누른 post
        HashMap<Long, Post> heartedPostMap = new HashMap<>();
        for (Heart heart : heartList) {
            heartedPostMap.put(heart.getPost().getId(), heart.getPost());
        }
        log.info("회원이 찜한 post hashMap 으로 변환");

        List<PostDto> postDtoList = postRepository.findAllPost()
                .stream().map(post -> {
                    boolean isHeart = heartedPostMap.containsKey(post.getId());
                    return PostDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .createdBy(post.getMember().getNickname())
                            .viewCount(post.getViewCount())
                            .isHeart(isHeart) // 현재 로그인한 회원이 찜 한 게시글인지 체크 | 했으면 true
                            .createdAt(post.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());
        log.info("postDtoList 변환 완료");
        return postDtoList;
    }

    public List<PostDto> findHeartPost(String username) {
        // 회원이 누른 heart 조회
        List<Long> postIdList = heartRepository.findByMemberId(username).stream()
                .map(heart -> heart.getPost().getId())
                .collect(Collectors.toList());

        List<PostDto> postDtoList = postRepository.findByPostIdList(postIdList).stream()
                .map(p -> PostDto.builder()
                        .postId(p.getId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .viewCount(p.getViewCount())
                        .createdBy(p.getMember().getNickname())
                        .isHeart(true)
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
        return postDtoList;
    }
}
