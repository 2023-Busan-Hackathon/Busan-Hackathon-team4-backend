package busanhackathon.team4.gptApi.repository;

import busanhackathon.team4.gptApi.entity.GptApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GptApiRepository extends JpaRepository<GptApi, Long> {
    @Query("select g from GptApi g where g.createdAt = (select max(gg.createdAt) from GptApi gg)")
    Optional<GptApi> findOneDesc();
}
