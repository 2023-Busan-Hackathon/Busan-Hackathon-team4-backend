package busanhackathon.team4.gptApi;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GptApiDto {
    private String gptResponse;
    private String food;
    private String ingredient;
    private String recipe;
}
