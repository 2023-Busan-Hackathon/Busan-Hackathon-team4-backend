package busanhackathon.team4.heart.service;

import busanhackathon.team4.heart.entity.Heart;
import busanhackathon.team4.heart.repository.HeartRepository;
import busanhackathon.team4.member.entity.Member;
import busanhackathon.team4.member.repository.MemberRepository;
import busanhackathon.team4.post.entity.Post;
import busanhackathon.team4.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HeartService {
    private final HeartRepository heartRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public Long enrollHeart(String username, Long postId) {
        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("없는 게시글입니다"));
        Heart heart = Heart.builder()
                .member(member)
                .post(post)
                .build();
        return heartRepository.save(heart).getId();
    }
}
