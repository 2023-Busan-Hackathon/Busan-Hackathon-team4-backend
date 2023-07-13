package busanhackathon.team4.recipe.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.recipe.dto.RecipeDto;
import busanhackathon.team4.recipe.service.RecipeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recipe")
    public ResponseEntity<Result> getRecipeList(@AuthenticationPrincipal User user) {
        List<RecipeDto> recipeDtoList = recipeService.findAllRecipeByMember(user.getUsername());
        return ResponseEntity.ok(new Result(recipeDtoList, "저장한 레시피 조회 완료"));

    }

    @PostMapping("/recipe")
    public ResponseEntity<Result> saveRecipe(@AuthenticationPrincipal User user,
                                             @RequestBody RecipeDto recipeDto) {
        Long recipeId = recipeService.saveRecipe(user.getUsername(), recipeDto);
        return ResponseEntity.ok(new Result(recipeId, "레시피 저장 완료"));
    }

    /**
     * recipeId 와 isPublic 값을 받아 update
     */
    @PostMapping("/recipe-change-public")
    public ResponseEntity<Result> changeRecipePublic(@AuthenticationPrincipal User user,
                                                     @RequestBody RecipeDto recipeDto) {
        recipeService.changePublic(user.getUsername(), recipeDto);
        return ResponseEntity.ok(new Result(null, "레시피 권한 " + recipeDto.getIsPublic() + "으로 변경" ));
    }

}
