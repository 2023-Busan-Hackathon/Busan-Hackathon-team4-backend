package busanhackathon.team4.food.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.food.dto.FoodDto;
import busanhackathon.team4.food.service.FoodService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FoodController {

    private final FoodService foodService;

    @ApiOperation(value = "못 먹는 음식 조회", notes = "요청 파라미터 없음")
    @GetMapping("/food")
    public ResponseEntity<Result> getFood(@AuthenticationPrincipal User user) {
        List<FoodDto> foodDtoList = foodService.findAllFoodByMember(user.getUsername());
        return ResponseEntity.ok(new Result(foodDtoList, "제외할 음식 조회 완료"));
    }

    @ApiOperation(value = "못 먹는 음식 추가", notes = "요청 파라미터:<br>foodName")
    @PostMapping("/food")
    public ResponseEntity<Result> banFood(@AuthenticationPrincipal User user, @RequestBody FoodDto foodDto) {
        Long foodId = foodService.enrollFood(user.getUsername(), foodDto);
        return ResponseEntity.ok(new Result(foodId, "제외할 음식 저장 완료"));
    }

    @ApiOperation(value = "못 먹는 음식 삭제", notes = "요청 파라미터 없음")
    @DeleteMapping("/food/{foodId}")
    public ResponseEntity<Result> removeBanFood(@PathVariable("foodId") Long foodId) {
        foodService.removeFood(foodId);
        return ResponseEntity.ok(new Result(null, "제외할 음식 삭제 완료"));
    }
}
