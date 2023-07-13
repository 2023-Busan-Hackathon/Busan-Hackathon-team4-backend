package busanhackathon.team4.recipe.repository;

import busanhackathon.team4.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("select r from Recipe r where r.member.id = :username")
    List<Recipe> findAllRecipeByMember(@Param("memberId") Long username);
}
