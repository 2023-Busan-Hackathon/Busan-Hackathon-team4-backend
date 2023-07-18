package busanhackathon.team4.gptApi.entity;

import busanhackathon.team4.common.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GptApi extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "gpt_api_id")
    private Long id;
    @Column(columnDefinition = "text")
    private String gptResponse;
    private String food;
    @Column(columnDefinition = "text")
    private String ingredient;
    @Column(columnDefinition = "text")
    private String recipe;
}
