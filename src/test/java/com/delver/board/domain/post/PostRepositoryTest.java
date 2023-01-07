package com.delver.board.domain.post;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
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
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void save() throws Exception {
        // given
        Member member = createMember();
        Post post = PostSaveRequestDto.builder()
                .title("제목")
                .content("내용")
                .member(member)
                .category("카테고리")
                .build()
                .toEntity();
        // when
        postRepository.save(post);

        // then
        Post findPost = postRepository.findById(post.getId());
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getContent()).isEqualTo("내용");
        assertThat(findPost.getMember()).isEqualTo(member);
        assertThat(findPost.getCategory()).isEqualTo("카테고리");
    }

    @Test
    public void delete() throws Exception {
        // given
        Member member = createMember();
        Post post = PostSaveRequestDto.builder()
                .title("제목")
                .content("내용")
                .member(member)
                .category("카테고리")
                .build()
                .toEntity();
        postRepository.save(post);

        Post findPost = postRepository.findById(post.getId());

        // when
        postRepository.delete(findPost);

        // then
        Post deletedPost = postRepository.findById(post.getId());

        assertThat(deletedPost).isNull();
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


}