package illvent.backend.post.service;

import illvent.backend.member.domain.Member;
import illvent.backend.member.domain.MemberRepository;
import illvent.backend.member.domain.MemberStatus;
import illvent.backend.post.domain.Post;
import illvent.backend.post.domain.PostRepository;
import illvent.backend.post.dto.PostRegisterRequestDTO;
import illvent.backend.post.dto.PostResponseDTO;
import illvent.backend.post.dto.PostUpdateRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Optional<Post> registerPost(PostRegisterRequestDTO postRegisterRequestDTO) {

        Member foundMember = memberRepository.findById(postRegisterRequestDTO.getMemberNo()).orElseThrow(() ->
                new IllegalArgumentException("Member not found"));

        if(foundMember.getStatus() == MemberStatus.N) throw new IllegalArgumentException("Invalid member");

        Post post = Post.builder()
                .title(postRegisterRequestDTO.getTitle())
                .content(postRegisterRequestDTO.getContent())
                .member(foundMember)
                .createDate(LocalDateTime.now())
                .build();

        return Optional.of(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long postNo){
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));

        postRepository.delete(post);
    }

    @Transactional
    public PostResponseDTO updatePost(Long postNo, PostUpdateRequestDTO postUpdateRequestDTO) {
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));

        post.update(postUpdateRequestDTO);

        postRepository.save(post);

        return new PostResponseDTO(post);
    }

    public List<PostResponseDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, 10);

        return postRepository.findAll(pageable).stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    public PostResponseDTO getPost(Long postNo) {
        Post post = postRepository.findById(postNo).orElseThrow(() ->
                new IllegalArgumentException("Post not found"));

        return new PostResponseDTO(post);
    }
}
