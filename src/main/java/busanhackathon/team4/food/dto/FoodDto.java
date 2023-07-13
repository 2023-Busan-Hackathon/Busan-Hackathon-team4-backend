package busanhackathon.team4.food.dto;

import lombok.*;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
public class FoodDto {
    private Long foodId;
    private String foodName;
}
