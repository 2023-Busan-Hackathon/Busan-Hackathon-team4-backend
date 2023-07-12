package busanhackathon.team4.member.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<Result> joinUser(@RequestBody JoinFormDto joinFormDto) {
        log.info("회원가입");
        return ResponseEntity.ok(new Result(memberService.saveMember(joinFormDto), "회원가입 완료"));
    }


    @Getter @Setter
    @AllArgsConstructor
    public static class JoinFormDto {
        private String loginId;
        private String password;
        private String nickname;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class LoginFormDto {
        private String loginId;
        private String password;
    }
}

