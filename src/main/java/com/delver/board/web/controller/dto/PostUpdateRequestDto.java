package com.delver.board.web.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequestDto {

    private String title;
    private String content;
    private String category;

    @Builder
    public PostUpdateRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

}
