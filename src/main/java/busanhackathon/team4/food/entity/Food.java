package busanhackathon.team4.food.entity;

import busanhackathon.team4.member.entity.Member;
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
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;
    private String foodName; //못먹는 음식 이름
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
