package com.delver.board.service;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.domain.post.Post;
import com.delver.board.domain.post.PostRepository;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.web.controller.dto.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long savePost(PostSaveRequestDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId);
        Post post = dto.toEntity(member);
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Post findById(Long postId) {
        return postRepository.findById(postId);
    }

    @Transactional(readOnly = true)
    public List<Post> findAll() {
        return postRepository.findAll();
    }


    @Transactional
    public void updatePost(Long post_id, PostUpdateRequestDto dto) {
        Post findPost = postRepository.findById(post_id);
        findPost.update(dto.getTitle(), dto.getContent(), dto.getCategory());
    }

    @Transactional
    public void deletePost(Long post_id) {
        Post findPost = postRepository.findById(post_id);
        postRepository.delete(findPost);
    }


}
