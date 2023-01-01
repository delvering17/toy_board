package com.delver.board.web.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String userName;
    private String email;
    private String picture;

    @Builder
    public MemberUpdateRequestDto(String userName, String email, String picture) {
        this.userName = userName;
        this.email = email;
        this.picture = picture;
    }
}
