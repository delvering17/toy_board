package com.delver.board.service;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.exception.MemberException;
import com.delver.board.exception.code.MemberExceptionCode;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long save(MemberSaveRequestDto dto) {

        checkPasswordAndPasswordConfirm(dto.getPassword(), dto.getPasswordConfirm());
        checkDuplicateEmail(dto.getEmail());

        Member member = dto.toEntity();
        Long memberId = memberRepository.save(member);

        return memberId;
    }



    @Transactional(readOnly = true)
    public Member findById(Long memberId) {
        Member member = memberRepository.findById(memberId);

        if (member == null) {
            throw new MemberException(MemberExceptionCode.MEMBER_NOT_FOUND);
        }

        return member;
    }

    @Transactional(readOnly = true)
    public Member findByUserName(String userName) {
        Member member = memberRepository.findByUserName(userName)
                .orElseThrow(() -> new MemberException(MemberExceptionCode.MEMBER_NOT_FOUND));

        return member;
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto dto) {

        Member member = memberRepository.findById(memberId);

        member.update(dto.getUserName(), dto.getPassword(), dto.getEmail());
    }

    private void checkPasswordAndPasswordConfirm(String password, String passwordConfirm) {

        if (!password.equals(passwordConfirm)) {
            throw new MemberException(MemberExceptionCode.MEMBER_INCONSISTENCY_PASSWORDCONFIRM);
        }
    }

    private void checkDuplicateEmail(String email) {
        memberRepository.findByLoginEmail(email)
                .ifPresent(m -> {
                    throw new MemberException(MemberExceptionCode.MEMBER_DUPLICATE_EMAIL);

                });
    }


}
