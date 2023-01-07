package com.delver.board.domain.post;

import com.delver.board.domain.member.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSaveRequestDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private Member member;

    @NotEmpty
    private String category;

    @Builder
    public PostSaveRequestDto(String title, String content, Member member, String category) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
    }

    public Post toEntity() {
        return Post.createPost()
                .title(title)
                .content(content)
                .member(member)
                .category(category)
                .build();
    }
}
