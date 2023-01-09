package com.delver.board.service;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.service.MemberService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.MemberUpdateRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Transactional
    @Test
    public void 회원_가입() throws Exception {
        // given
        MemberSaveRequestDto dto = createMemberSaveRequestDto();

        // when
        memberService.save(dto);

        // then
        Member findMember = memberService.findByUserName(dto.getUserName());

        assertThat(findMember.getUserName()).isEqualTo("delver");
        assertThat(findMember.getPassword()).isEqualTo("password");
        assertThat(findMember.getEmail()).isEqualTo("delvering17@gmail.com");
        assertThat(findMember.getRole()).isEqualTo(Role.USER);
        assertThat(findMember.getJoinRoot()).isEqualTo(JoinRoot.LOCAL);
    }

    @Transactional
    @Test
    public void 비밀번호_비밀번호확인_일치하지_않으면_exception() throws Exception {
        // given
        String password = "password";
        String passwordConfirm = "passwordConfirm";

        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password(password)
                .passwordConfirm(passwordConfirm)
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        // when
        // then
        assertThatThrownBy(() -> memberService.save(dto))
                .isInstanceOf(IllegalArgumentException.class);

    }



    @Transactional
    @Test
    public void 회원_정보_없으면_exception() throws Exception {
        assertThatThrownBy(() -> memberService.findByUserName("nobody"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Transactional
    @Test
    public void 회원_정보_수정() throws Exception {
        // given
        MemberSaveRequestDto saveDto = createMemberSaveRequestDto();
        memberService.save(saveDto);

        // when
        MemberUpdateRequestDto updateDto = MemberUpdateRequestDto.builder()
                .userName("aaaa")
                .email("aaaa@gmail.com")
                .password("aaaaa")
                .passwordConfirm("aaaaa")
                .build();
        memberService.updateMember(1L, updateDto);

        // then
        Member updateMember = memberService.findByUserName(updateDto.getUserName());

        assertThat(updateMember.getUserName()).isEqualTo("aaaa");
        assertThat(updateMember.getEmail()).isEqualTo("aaaa@gmail.com");
        assertThat(updateMember.getPassword()).isEqualTo("aaaaa");

    }

    private MemberSaveRequestDto createMemberSaveRequestDto() {
        return MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
    }

}