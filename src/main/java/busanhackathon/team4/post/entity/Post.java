package busanhackathon.team4.post.entity;

import busanhackathon.team4.common.BaseEntity;
import busanhackathon.team4.heart.entity.Heart;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.recipe.entity.Recipe;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "text")
    private String content;
    @Builder.Default
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Heart> heartList;

    // 조회수 1 증가
    public void increaseViewCount() {
        this.viewCount++;
    }
}
