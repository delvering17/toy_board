package com.delver.board.web.controller.dto;

import com.delver.board.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;

    private String title;
    private String content;
    private String username;
    private String category;

    private LocalDateTime createDate;

    @Builder
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getMember().getUserName();
        this.category = post.getCategory();
        this.createDate = post.getCreateDate();
    }


}
