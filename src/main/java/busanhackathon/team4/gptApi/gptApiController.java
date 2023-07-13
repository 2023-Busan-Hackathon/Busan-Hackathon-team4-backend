package busanhackathon.team4.gptApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
public class gptApiController {
    @Autowired
    private gptApiService openAIService;

    @GetMapping("/ai")
    public ResponseEntity<?> openAiGPT(@RequestParam(required = false) String query,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(required = false) Integer display) {
        log.info("ai 컨트롤러 진입");
        try {
            return ResponseEntity.ok().body(openAIService.getCompletion(query));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
