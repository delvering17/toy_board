package com.delver.board.domain.member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final EntityManager em;
    public void save(Member member) {
        em.persist(member);
        log.info("member.id={}", member.getId());
    }

    public Member findById(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
