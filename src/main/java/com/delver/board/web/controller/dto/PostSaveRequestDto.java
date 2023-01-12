package com.delver.board.web.controller.dto;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.post.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveRequestDto {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotEmpty
    private String category;

    @Builder
    public PostSaveRequestDto(String title, String content, String category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Post toEntity(Member member) {
        return Post.createPost()
                .title(title)
                .content(content)
                .category(category)
                .member(member)
                .build();
    }

}
