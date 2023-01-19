package com.delver.board.service;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.exception.PostException;
import com.delver.board.web.controller.dto.PostResponseDto;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.web.controller.dto.PostUpdateRequestDto;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    private EntityManager em;


    @Test
    public void 게시글_포스트() throws Exception {
        // given
        Member member = createMember();
        PostSaveRequestDto dto = createPostSaveRequestDto( "제목", "내용", member, "카테고리");

        // when
        Long post_id = postService.savePost(dto, member.getId());

        // then
        PostResponseDto findPost = postService.findById(post_id);
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getContent()).isEqualTo("내용");
        assertThat(findPost.getUsername()).isEqualTo(member.getUserName());
        assertThat(findPost.getCategory()).isEqualTo("카테고리");
    }

    @Test
    public void 게시글_수정() throws Exception {
        // given
//        Post ff = postService.findById(1L);
//        log.info("ff={}", ff.getTitle());
        Member member = createMember();
        PostSaveRequestDto saveDto = createPostSaveRequestDto("제목", "내용", member, "카테고리");

        Long post_id = postService.savePost(saveDto, member.getId());
        // when
        PostUpdateRequestDto updateDto = createPostUpdateRequestDto("수정된 제목", "수정된 내용", "수정된 카테고리");
        postService.updatePost(post_id, updateDto);

        // then
        PostResponseDto dto = postService.findById(post_id);

        assertThat(dto.getTitle()).isEqualTo("수정된 제목");
        assertThat(dto.getContent()).isEqualTo("수정된 내용");
        assertThat(dto.getUsername()).isEqualTo(member.getUserName());
        assertThat(dto.getCategory()).isEqualTo("수정된 카테고리");

    }

    @Test
    public void 게시글_삭제() throws Exception {
        // given
        Member member = createMember();
        PostSaveRequestDto dto = createPostSaveRequestDto("제목", "내용", member, "카테고리");
        Long post_id = postService.savePost(dto, member.getId());


        // when
        postService.deletePost(post_id);

        // then
        assertThatThrownBy(() -> postService.findById(post_id))
                .isInstanceOf(PostException.class);

    }

    @Test
    public void 해당_게시글이_없으면_excpetion() throws Exception {
        assertThatThrownBy(() -> postService.findById(2100L))
                .isInstanceOf(Exception.class);
    }

    private Member createMember() {
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .password("password")
                .passwordConfirm("password")
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