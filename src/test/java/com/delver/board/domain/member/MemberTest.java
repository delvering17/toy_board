package com.delver.board.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    public void member_username_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Member.createMember()
                .userName("")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void member_email_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Member.createMember()
                .userName("delver")
                .email("")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void member_picture_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void member_role_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(null)
                .joinRoot(JoinRoot.LOCAL)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void member_joinRoot_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(null)
                .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void createMember() throws Exception {
        Member member = Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        assertThat(member.getUserName()).isEqualTo("delver");
        assertThat(member.getEmail()).isEqualTo("delvering17@gmail.com");
        assertThat(member.getPicture()).isEqualTo("picture");
        assertThat(member.getRole()).isEqualTo(Role.USER);
        assertThat(member.getJoinRoot()).isEqualTo(JoinRoot.LOCAL);
    }

    @Test
    public void update_정보수정() throws Exception {
        // given
        Member member = Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        // when
        member.update("newMember", "dog@cat.com", "cat");

        // then
        assertThat(member.getUserName()).isEqualTo("newMember");
        assertThat(member.getEmail()).isEqualTo("dog@cat.com");
        assertThat(member.getPicture()).isEqualTo("cat");
    }
}