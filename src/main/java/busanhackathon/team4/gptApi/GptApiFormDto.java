package busanhackathon.team4.gptApi;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GptApiFormDto {
    private String ingredient;
    private String difficulty;
}
