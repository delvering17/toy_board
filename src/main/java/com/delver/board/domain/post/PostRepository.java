package com.delver.board.domain.post;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private final EntityManager em;

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public Optional<Post> findById(Long postId) {
        return em.createQuery("select p from Post p" +
                        " join fetch p.member where p.id = :postId", Post.class)
                .setParameter("postId", postId)
                .getResultList().stream().findFirst();

    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findPage(int offset, int limit) {
        return em.createQuery("select p from Post p " +
                        " join fetch p.member " +
                        " order by p.createDate DESC", Post.class)
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
