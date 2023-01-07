package com.delver.board.domain.post;

import lombok.Builder;
import lombok.Getter;

@Getter
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
