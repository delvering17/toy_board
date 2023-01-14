package com.delver.board.service;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.domain.post.Post;
import com.delver.board.domain.post.PostRepository;
import com.delver.board.web.constant.PostConst;
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
    public List<Post> findPage(int page, int limit) {
        return postRepository.findPage( (page - 1) * limit, limit);
    }

    @Transactional(readOnly = true)
    public Long countPage() {
        Long countPost = postRepository.countPost();
        return calculateCountPage(countPost);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto dto) {
        Post findPost = postRepository.findById(postId);
        findPost.update(dto.getTitle(), dto.getContent(), dto.getCategory());
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId);
        postRepository.delete(findPost);
    }

    private static long calculateCountPage(Long countPost) {
        if (countPost % PostConst.POST_LIMIT > 0) {
            return countPost / PostConst.POST_LIMIT + 1;
        }
        return countPost / PostConst.POST_LIMIT;
    }


}
