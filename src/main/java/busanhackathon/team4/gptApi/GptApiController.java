package busanhackathon.team4.gptApi;

import busanhackathon.team4.common.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GptApiController {

    private final GptApiService service;

    @ApiOperation(value = "api 에서 받아온 레시피 저장", notes = "요청 파라미터:<br>foodName<br>method")
    @GetMapping("/ai")
    public ResponseEntity<?> openAiGPT(@AuthenticationPrincipal User user,
                                       @RequestBody GptApiFormDto gptApiFormDto) {

        log.info("ai 컨트롤러 진입");
        try {
            return ResponseEntity.ok().body(new Result(service.callApi(user.getUsername(), gptApiFormDto), "gpt api 응답 데이터 반환 완료"));
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}