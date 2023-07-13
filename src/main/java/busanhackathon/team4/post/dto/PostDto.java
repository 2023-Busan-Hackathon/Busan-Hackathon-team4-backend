package busanhackathon.team4.post.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class PostDto {
    private Long postId;
    private Long recipeId; // 저장된 레시피에서 게시글 작성했을 때 공개 여부 update 하기 위해 필요
    private String title;
    private String content;
    private Integer viewCount; // 조회수
    private String createdBy; // 작성자 
    private Boolean isHeart; // 찜 했는지 체크
    private LocalDateTime createdAt; // 생성 시간
}
