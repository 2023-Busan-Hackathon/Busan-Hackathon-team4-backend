package busanhackathon.team4.food.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.food.dto.FoodDto;
import busanhackathon.team4.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/food")
    public ResponseEntity<Result> getFood(@AuthenticationPrincipal User user) {
        List<FoodDto> foodDtoList = foodService.findAllFoodByMember(user.getUsername());
        return ResponseEntity.ok(new Result(foodDtoList, "제외할 음식 조회 완료"));
    }

    @PostMapping("/food")
    public ResponseEntity<Result> banFood(@AuthenticationPrincipal User user, FoodDto foodDto) {
        Long foodId = foodService.enrollFood(user.getUsername(), foodDto);
        return ResponseEntity.ok(new Result(foodId, "제외할 음식 저장 완료"));
    }

    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<Result> removeBanFood(@PathVariable("foodId") Long foodId) {
        foodService.removeFood(foodId);
        return ResponseEntity.ok(new Result(null, "제외할 음식 삭제 완료"));
    }
}
