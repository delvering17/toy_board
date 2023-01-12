package com.delver.board.web.controller.dto;

import com.delver.board.domain.post.Post;
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

    @Builder(builderMethodName = "changeEntityToDto", builderClassName = "changeEntityToDto")
    public PostUpdateRequestDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
    }

    @Builder
    public PostUpdateRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }



}
