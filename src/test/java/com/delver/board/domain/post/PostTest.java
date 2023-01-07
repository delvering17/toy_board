package com.delver.board.domain.post;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.domain.post.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PostTest {
    private Member member;
    @BeforeEach
    public void beforeEach() {
        member = Member.createMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.GOOGLE)
                .build();
    }

    @AfterEach
    public void afterEach() {
        member = null;
    }

    @Test
    public void post_title_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Post.createPost()
                                        .title("")
                                        .content("내용")
                                        .member(member)
                                        .category("카테고리")
                                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    public void post_content_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Post.createPost()
                                        .title("제목")
                                        .content("")
                                        .member(member)
                                        .category("카테고리")
                                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void post_member_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Post.createPost()
                                        .title("제목")
                                        .content("")
                                        .member(null)
                                        .category("카테고리")
                                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void post_category_비어있으면_exception() throws Exception {
        assertThatThrownBy(() -> Post.createPost()
                                        .title("제목")
                                        .content("")
                                        .member(member)
                                        .category("")
                                        .build())
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    public void createPost() throws Exception {
        Post post = Post.createPost()
                .title("제목")
                .content("내용")
                .member(member)
                .category("카테고리")
                .build();

        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("내용");
        assertThat(post.getMember().getUserName()).isEqualTo("delver");
        assertThat(post.getCategory()).isEqualTo("카테고리");
    }

    @Test
    public void member_연관관계_매서드() throws Exception {
        // given
        Post post = Post.createPost()
                .title("제목")
                .content("내용")
                .member(member)
                .category("카테고리")
                .build();

        // when
        Post findPost = member.getPosts().get(0);

        // then
        assertThat(findPost).isEqualTo(post);
    }
}