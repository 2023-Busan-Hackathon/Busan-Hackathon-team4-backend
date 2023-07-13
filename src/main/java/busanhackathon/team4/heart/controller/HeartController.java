package busanhackathon.team4.heart.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.heart.repository.HeartRepository;
import busanhackathon.team4.heart.service.HeartService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HeartController {

    private final HeartService heartService;
    @ApiOperation(value = "찜 등록", notes = "요청 파라미터 없음")
    @PostMapping("/heart/{postId}")
    public ResponseEntity<Result> enrollHeart(@AuthenticationPrincipal User user,
                                              @PathVariable("postId") Long postId) {
        Long heartId = heartService.enrollHeart(user.getUsername(), postId);
        return ResponseEntity.ok(new Result(heartId, "찜 완료"));
    }
}
