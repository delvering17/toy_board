package com.delver.board.service;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveRequestDto dto) {
        Member member = dto.toEntity();
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId);

        if (member == null) {
            throw new IllegalStateException("회원 정보가 없습니다.");
        }

        return member;
    }

    @Transactional(readOnly = true)
    public Member findByUserName(String userName) {
        Member member;
        try {
             member = memberRepository.findByUserName(userName);

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalStateException("회원 정보가 없습니다.", e);
        }

        return member;
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto dto) {
        Member member = memberRepository.findById(memberId);
        member.update(dto.getUserName(), dto.getEmail(), dto.getPicture());
    }

}
