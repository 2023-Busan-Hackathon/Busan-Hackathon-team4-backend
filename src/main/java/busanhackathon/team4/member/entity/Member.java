package busanhackathon.team4.member.entity;

import busanhackathon.team4.common.BaseEntity;
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
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String nickname;
    @Builder.Default
    private String role = "USER";
}
