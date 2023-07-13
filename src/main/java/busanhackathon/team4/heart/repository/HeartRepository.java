package busanhackathon.team4.heart.repository;

import busanhackathon.team4.heart.entity.Heart;
import busanhackathon.team4.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    @Query("select h from Heart h join fetch h.post p where h.member.loginId = :username")
    List<Heart> findByMemberId(@Param("username") String username);

}
