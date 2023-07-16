package busanhackathon.team4.recipe.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.recipe.dto.RecipeDto;
import busanhackathon.team4.recipe.service.RecipeService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;

    @ApiOperation(value = "저장한 레시피 전체 조회", notes = "요청 파라미터 없음")
    @GetMapping("/recipe")
    public ResponseEntity<Result> getRecipeList(@AuthenticationPrincipal User user) {
        List<RecipeDto> recipeDtoList = recipeService.findAllRecipeByMember(user.getUsername());
        return ResponseEntity.ok(new Result(recipeDtoList, "저장한 레시피 조회 완료"));
    }
    
    @ApiOperation(value = "저장한 레시피 상세 조회", notes = "요청 파라미터 없음")
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<Result> getRecipeDetail(@PathVariable("recipeId") Long recipeId) {
        RecipeDto recipeDto = recipeService.findOneById(recipeId);
        return ResponseEntity.ok(new Result(recipeDto, "저장한 레시피 상세 조회"));
    }

    @ApiOperation(value = "api 에서 받아온 레시피 저장", notes = "요청 파라미터:<br>foodName<br>method")
    @PostMapping("/recipe")
    public ResponseEntity<Result> saveRecipe(@AuthenticationPrincipal User user,
                                             @RequestBody RecipeDto recipeDto) {
        Long recipeId = recipeService.saveRecipe(user.getUsername(), recipeDto);
        return ResponseEntity.ok(new Result(recipeId, "레시피 저장 완료"));
    }

    @ApiOperation(value = "api 에서 받아온 레시피 삭제", notes = "요청 파라미터 없음")
    @DeleteMapping("/recipe/{recipeId}")
    public ResponseEntity<Result> removeRecipe(@AuthenticationPrincipal User user,
                                             @PathVariable("recipeId") Long recipeId) {
        recipeService.removeRecipe(user.getUsername(), recipeId);
        return ResponseEntity.ok(new Result(recipeId, "레시피 삭제 완료. (추가로 게시글, 좋아요도 삭제 완료"));
    }



}
