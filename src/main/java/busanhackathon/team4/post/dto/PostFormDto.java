package busanhackathon.team4.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostFormDto {
    @Schema(title = "게시글 제목(음식 이름)", example = "김치 계란 볶음밥")
    private String title;
    @Schema(title = "레시피 ID", example = "레시피 pk 입력 ex)1")
    private Long recipeId;
    @Schema(title = "게시글 내용(레시피)", example = "김치를 넣고 밥을 넣고 계란을 넣고 볶고 볶고")
    private String content;
}
