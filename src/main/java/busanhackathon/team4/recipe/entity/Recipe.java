package busanhackathon.team4.recipe.entity;

import busanhackathon.team4.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    private String foodName;
    @Column(length = 50000)
    private String method;
    private Boolean isPublic;

    @OneToOne(mappedBy = "recipe")
    private Post post;
}
