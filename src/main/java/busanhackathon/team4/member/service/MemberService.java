package busanhackathon.team4.member.service;

import busanhackathon.team4.member.controller.MemberController;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public Long saveMember(MemberController.JoinFormDto joinFormDto) {
        Member member = Member.builder()
                .loginId(joinFormDto.getLoginId())
                .password(joinFormDto.getPassword())
                .nickname(joinFormDto.getNickname())
                .build();
        return memberRepository.save(member).getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("loginId에 해당하는 회원이 없습니다."));
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
}
