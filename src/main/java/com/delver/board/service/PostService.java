package com.delver.board.service;

import com.delver.board.domain.post.Post;
import com.delver.board.domain.post.PostRepository;
import com.delver.board.domain.post.PostSaveRequestDto;
import com.delver.board.domain.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long savePost(PostSaveRequestDto dto) {
        Post post = dto.toEntity();
        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public Post findById(Long post_id) {
        return postRepository.findById(post_id);
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
