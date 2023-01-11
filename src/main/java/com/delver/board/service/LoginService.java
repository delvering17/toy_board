package com.delver.board.service;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.exception.LoginException;
import com.delver.board.exception.code.LoginExceptionCode;
import com.delver.board.web.controller.dto.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member login(LoginRequestDto dto) {

        Member member = memberRepository.findByLoginEmail(dto.getEmail())
                .orElseThrow(() -> new LoginException(LoginExceptionCode.LOGIN_NOTFOUND_MEMBER));

        if (!dto.getPassword().equals(member.getPassword())) {
            throw new LoginException(LoginExceptionCode.LOGIN_PASSWORD_MISMATCH);
        }

        return member;
    }
}
