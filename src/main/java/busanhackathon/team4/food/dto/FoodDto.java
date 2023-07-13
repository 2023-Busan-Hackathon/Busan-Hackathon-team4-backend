package busanhackathon.team4.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@Getter @Setter
@AllArgsConstructor
@Builder
public class FoodDto {
    @Schema(title = "못 먹는 음식 ID", example = "1")
    private Long foodId;
    @Schema(title = "못 먹는 음식 이름", example = "오이")
    private String foodName;
}
