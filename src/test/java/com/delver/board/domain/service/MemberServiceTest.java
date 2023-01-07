package com.delver.board.domain.service;

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
        assertThat(findMember.getEmail()).isEqualTo("delvering17@gmail.com");
        assertThat(findMember.getRole()).isEqualTo(Role.USER);
        assertThat(findMember.getJoinRoot()).isEqualTo(JoinRoot.LOCAL);
    }



    @Transactional
    @Test
    public void 회원_정보_없으면_exception() throws Exception {
        assertThatThrownBy(() -> memberService.findByUserName("nobody"))
                .isInstanceOf(IllegalStateException.class);
        assertThatThrownBy(() -> memberService.findById(1L))
                .isInstanceOf(IllegalStateException.class);

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
                .picture("bbbb")
                .build();
        memberService.updateMember(1L, updateDto);

        // then
        Member updateMember = memberService.findByUserName(updateDto.getUserName());

        assertThat(updateMember.getUserName()).isEqualTo("aaaa");
        assertThat(updateMember.getEmail()).isEqualTo("aaaa@gmail.com");
        assertThat(updateMember.getPicture()).isEqualTo("bbbb");
    }

    private MemberSaveRequestDto createMemberSaveRequestDto() {
        return MemberSaveRequestDto.builder()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
    }

}