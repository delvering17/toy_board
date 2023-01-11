package com.delver.board.domain.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;
    public Long save(Member member) {
        em.persist(member);
        log.info("member.id={}", member.getId());

        return member.getId();
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Optional<Member> findByUserName(String userName) {
        String sql = "select m from Member m where m.userName = :userName";
        Optional<Member> member = em.createQuery(sql, Member.class)
                .setParameter("userName", userName)
                .getResultStream().findFirst();
        return member;
    }

    public Optional<Member> findByLoginEmail(String email) {
        String sql = "select m from Member m where m.email = :email";
        Optional<Member> member = em.createQuery(sql, Member.class)
                .setParameter("email", email)
                .getResultStream().findFirst();
        return member;
    }
}
