package busanhackathon.team4.gptApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class gptApiController {

    private final gptApiService service;

    @GetMapping("/ai")
    public ResponseEntity<?> openAiGPT(@RequestParam(required = false) String query,
                                       @RequestParam(required = false) String sort,
                                       @RequestParam(required = false) Integer display) {
        log.info("ai 컨트롤러 진입");
        log.info("컨트롤러 query: " + query);
        try {
            return ResponseEntity.ok().body(service.callApi(query));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}