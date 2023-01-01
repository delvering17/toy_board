package com.delver.board.domain.service;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.service.MemberService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.MemberUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void 회원_가입() throws Exception {
        // given
        MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        // when
        memberService.save(dto);

        // then
        Member findMember = memberService.findById(1L);
        assertThat(findMember.getUserName()).isEqualTo("delver");
        assertThat(findMember.getEmail()).isEqualTo("delvering17@gmail.com");
        assertThat(findMember.getRole()).isEqualTo(Role.USER);
        assertThat(findMember.getJoinRoot()).isEqualTo(JoinRoot.LOCAL);
    }

    @Test
    public void 회원_정보_없으면_exception() throws Exception {
        // given
        MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        memberService.save(dto);
        // when
        // then

        assertThatThrownBy(() -> memberService.findById(2L))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void 회원_정보_수정() throws Exception {
        // given
        MemberSaveRequestDto saveDto = MemberSaveRequestDto.builder()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        memberService.save(saveDto);

        // when
        MemberUpdateRequestDto updateDto = MemberUpdateRequestDto.builder()
                .userName("aaaa")
                .email("aaaa@gmail.com")
                .picture("bbbb")
                .build();
        memberService.updateMember(1L, updateDto);

        // then
        Member updateMember = memberService.findById(1L);

        assertThat(updateMember.getUserName()).isEqualTo("aaaa");
        assertThat(updateMember.getEmail()).isEqualTo("aaaa@gmail.com");
        assertThat(updateMember.getPicture()).isEqualTo("bbbb");
    }

}