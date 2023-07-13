package busanhackathon.team4.recipe.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RecipeDto {
    private Long recipeId;
    private String foodName;
    private String method;
    private Boolean isPublic;
}
