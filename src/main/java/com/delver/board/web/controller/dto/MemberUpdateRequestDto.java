package com.delver.board.web.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String userName;

    private String password;

    private String passwordConfirm;
    private String email;

    @Builder
    public MemberUpdateRequestDto(String userName, String password, String passwordConfirm, String email) {
        this.userName = userName;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }
}
