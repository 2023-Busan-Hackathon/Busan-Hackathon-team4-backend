package busanhackathon.team4.food.repository;

import busanhackathon.team4.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("select f from Food f where f.member.id =: memberId")
    List<Food> findByMemberId(Long memberId);
}
