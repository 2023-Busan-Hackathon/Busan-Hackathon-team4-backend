package busanhackathon.team4.recipe.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDto {
    @Schema(title = "레시피 id", example = "3")
    private Long recipeId;
    @Schema(title = "음식 이름", example = "계란 김치 볶음밥")
    private String foodName;
    @Schema(title = "조리 방법", example = "1. ~~~~ 2. ~~~~")
    private String method;
    @Schema(title = "공개 여부", example = "true")
    private Boolean isPublic;
}
