package busanhackathon.team4.gptApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class gptApiService {

    private static final String API_ENDPOINT = "https://api.openai.com/v1/";

//    @Value("${OPEN_AI_KEY}")
    private String OPEN_AI_KEY = "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";
    private final RestTemplate restTemplate = restTemplate();

    public String getCompletion(String prompt) {
        String url = "https://api.openai.com/v1/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPEN_AI_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        // 요청 질문
        requestBody.put("prompt", prompt);

        // 요청에 사용될 모델 설정
        requestBody.put("model", "text-davinci-003");

        // 완료시 생성할 최대 토큰수
        requestBody.put("max_tokens", 1500);
        log.info("requestBody: " + requestBody);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("responseBody: " + response.getBody());
            return response.getBody();
        } catch (RestClientException e) {
            throw new OpenAIException("OpenAI API 호출 중 오류가 발생하였습니다.", e);
        }
    }
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            request.getHeaders().setBearerAuth(OPEN_AI_KEY);
            return execution.execute(request, body);
        });
        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }
    public class OpenAIException extends RestClientException {
        public OpenAIException(String message) {
            super(message);
        }

        public OpenAIException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}