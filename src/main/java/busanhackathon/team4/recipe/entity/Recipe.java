package busanhackathon.team4.recipe.entity;

import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.post.entity.Post;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    private String foodName;
    @Column(columnDefinition = "text")
    private String method;
    private Boolean isPublic;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
