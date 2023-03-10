package com.delver.board.domain.member;

import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    @Test
    public void save() throws Exception {
        // given
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        Member member = dto.toEntity();
        // when
        memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(member.getId());
        assertThat(member).isEqualTo(findMember);

    }

}