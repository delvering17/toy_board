package com.delver.board.domain.post;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Post findById(Long postId) {
        return em.find(Post.class, postId);
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findPage(int offset, int limit) {
        return em.createQuery("select p from Post p order by p.createDate DESC", Post.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long countPost() {
        return em.createQuery("select count(p) from Post p", Long.class)
                .getSingleResult();
    }

    public void delete(Post post) {
        em.remove(post);
    }

}
