package com.delver.board.service;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.domain.post.Post;
import com.delver.board.domain.post.PostSaveRequestDto;
import com.delver.board.domain.post.PostUpdateRequestDto;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    private EntityManager em;

    @Transactional
    @Test
    public void 게시글_포스트() throws Exception {
        // given
        Member member = createMember();
        PostSaveRequestDto dto = createPostSaveRequestDto( "제목", "내용", member, "카테고리");

        // when
        Long post_id = postService.savePost(dto);

        // then
        Post findPost = postService.findById(post_id);
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getContent()).isEqualTo("내용");
        assertThat(findPost.getMember()).isEqualTo(member);
        assertThat(findPost.getCategory()).isEqualTo("카테고리");
    }



    @Transactional
    @Test
    public void 게시글_수정() throws Exception {
        // given
//        Post ff = postService.findById(1L);
//        log.info("ff={}", ff.getTitle());
        Member member = createMember();
        PostSaveRequestDto saveDto = createPostSaveRequestDto("제목", "내용", member, "카테고리");

        Long post_id = postService.savePost(saveDto);
        // when
        PostUpdateRequestDto updateDto = createPostUpdateRequestDto("수정된 제목", "수정된 내용", "수정된 카테고리");
        postService.updatePost(post_id, updateDto);

        // then
        Post updatePost = postService.findById(post_id);

        assertThat(updatePost.getTitle()).isEqualTo("수정된 제목");
        assertThat(updatePost.getContent()).isEqualTo("수정된 내용");
        assertThat(updatePost.getMember()).isEqualTo(member);
        assertThat(updatePost.getCategory()).isEqualTo("수정된 카테고리");

    }

    @Transactional
    @Test
    public void 게시글_삭제() throws Exception {
        // given
        Member member = createMember();
        PostSaveRequestDto dto = createPostSaveRequestDto("제목", "내용", member, "카테고리");
        Long post_id = postService.savePost(dto);

        // when
        postService.deletePost(post_id);

        // then
        Post post = postService.findById(post_id);
        assertThat(post).isNull();

    }


    private Member createMember() {
        MemberSaveRequestDto dto = MemberSaveRequestDto.builder()
                .userName("delver")
                .email("delvering17@gmail.com")
                .picture("picture")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        Member member = dto.toEntity();
        em.persist(member);
        return member;
    }
    
    private static PostSaveRequestDto createPostSaveRequestDto(String title, String content, Member member, String category) {
        return PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .member(member)
                .category(category)
                .build();
    }

    private static PostUpdateRequestDto createPostUpdateRequestDto(String title, String content, String category) {
        return PostUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .category(category)
                .build();
    }


}