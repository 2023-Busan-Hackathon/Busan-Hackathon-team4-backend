package busanhackathon.team4.post.repository;

import busanhackathon.team4.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p join fetch p.member m")
    List<Post> findAllPost();

    @Query("select p from Post p where p.recipe.id = :recipeId")
    Optional<Post> findByRecipeId(@Param("recipeId") Long recipeId);

    @Query("select p from Post p join fetch p.member m where p.id in :postIdList")
    List<Post> findByPostIdList(@Param("postIdList") List<Long> postIdList);
}
