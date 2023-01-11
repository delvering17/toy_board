package com.delver.board.web.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    public LoginRequestDto() {
    }

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
