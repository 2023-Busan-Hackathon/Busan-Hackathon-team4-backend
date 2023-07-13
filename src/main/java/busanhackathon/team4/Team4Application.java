package busanhackathon.team4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //auditing 추가
@SpringBootApplication
public class Team4Application {

	public static void main(String[] args) {
		SpringApplication.run(Team4Application.class, args);
	}

}
