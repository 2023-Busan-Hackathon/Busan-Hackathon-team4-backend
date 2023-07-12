package busanhackathon.team4.member.service;

import busanhackathon.team4.member.controller.MemberController;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    /**
     * 회원가입
     */
    public Long saveMember(MemberController.JoinFormDto joinFormDto) {
        
        //중복체크
        Optional<Member> existMember = memberRepository.findByLoginId(joinFormDto.getLoginId());
        if(!existMember.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");

        Member member = Member.builder()
                .loginId(joinFormDto.getLoginId())
                .password(encoder.encode(joinFormDto.getPassword()))
                .nickname(joinFormDto.getNickname())
                .build();
        return memberRepository.save(member).getId();
    }




    /**
     * 스프링 시큐리티 로그인 체크 부분
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("로그인 중");
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("loginId에 해당하는 회원이 없습니다."));
        log.info("password"+ member.getPassword());
        //@AuthentcationPrincipal User user
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
