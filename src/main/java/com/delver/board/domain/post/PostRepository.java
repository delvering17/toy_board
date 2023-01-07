package com.delver.board.domain.post;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long post_id) {
        return em.find(Post.class, post_id);
    }

    public void delete(Post post) {
        em.remove(post);
    }

}
