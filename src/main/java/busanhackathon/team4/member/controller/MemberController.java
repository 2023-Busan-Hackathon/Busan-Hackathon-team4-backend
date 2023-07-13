package busanhackathon.team4.member.controller;

import busanhackathon.team4.common.Result;
import busanhackathon.team4.member.service.MemberService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
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
    @NoArgsConstructor
    public static class JoinFormDto {
        @Schema(title = "로그인 아이디", example = "abcde2")
        private String loginId;
        @Schema(title = "비밀번호", example = "abcde2")
        private String password;
        @Schema(title = "회원 닉네임", example = "닉네임")
        private String nickname;
    }

    @Getter @Setter
    @AllArgsConstructor
    public static class LoginFormDto {
        private String loginId;
        private String password;
    }
}