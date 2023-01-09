package com.delver.board.web.controller.dto;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveRequestDto {

    private String userName;

    private String password;
    private String passwordConfirm;
    private String email;
    private Role role;

    private JoinRoot joinRoot;

    @Builder(builderMethodName = "createLocalMember")
    public MemberSaveRequestDto(String userName, String password, String passwordConfirm, String email, Role role, JoinRoot joinRoot) {
        this.userName = userName;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.role = Role.USER;
        this.joinRoot = JoinRoot.LOCAL;
    }

    public Member toEntity() {
        return Member.createMember()
                .userName(userName)
                .password(password)
                .email(email)
                .role(role)
                .joinRoot(joinRoot)
                .build();
    }

}

