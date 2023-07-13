package busanhackathon.team4.food.service;

import busanhackathon.team4.food.dto.FoodDto;
import busanhackathon.team4.food.entity.Food;
import busanhackathon.team4.food.repository.FoodRepository;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final MemberRepository memberRepository;

    public Long enrollFood(String username, FoodDto foodDto) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));
        Food food = Food.builder().foodName(foodDto.getFoodName()).member(member).build();
        foodRepository.save(food);
        log.info("제외할 음식 저장");
        return food.getId();
    }

    public void removeFood(Long foodId) {
        foodRepository.deleteById(foodId);
        log.info("제외할 음식 제거");
    }

    public List<FoodDto> findAllFoodByMember(String username) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("없는 회원 입니다."));
        List<FoodDto> foodDtoList = foodRepository.findByMemberId(member.getId()).stream()
                .map(food -> FoodDto.builder()
                        .foodId(food.getId())
                        .foodName(food.getFoodName())
                        .build())
                .collect(Collectors.toList());
        return foodDtoList;
    }
}
