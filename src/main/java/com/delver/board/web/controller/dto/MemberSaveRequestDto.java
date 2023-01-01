package com.delver.board.web.controller.dto;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSaveRequestDto {

    private String userName;
    private String email;
    private String picture;
    private Role role;
    private JoinRoot joinRoot;

    @Builder
    public MemberSaveRequestDto(String userName, String email, String picture, Role role, JoinRoot joinRoot) {
        this.userName = userName;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.joinRoot = joinRoot;
    }

    public Member toEntity() {
        return Member.createMember()
                .userName(userName)
                .email(email)
                .picture(picture)
                .role(role)
                .joinRoot(joinRoot)
                .build();
    }

}

