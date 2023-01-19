package com.delver.board.domain.post;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.Role;
import com.delver.board.exception.PostException;
import com.delver.board.web.constant.PostConst;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        Post post = createPostEntity(member);

        // when
        postRepository.save(post);

        // then
        Post findPost = postRepository.findById(post.getId()).get();
        assertThat(findPost.getTitle()).isEqualTo("제목");
        assertThat(findPost.getContent()).isEqualTo("내용");
        assertThat(findPost.getMember()).isEqualTo(member);
        assertThat(findPost.getCategory()).isEqualTo("카테고리");
    }



    @Test
    public void findAll() throws Exception {
        // given
        Member member = createMember();
        for (int i = 0; i < 5; i++) {
            Post post = createPostEntity(member);
            postRepository.save(post);
        }

        // when
        List<Post> postList = postRepository.findAll();

        // then
        assertThat(postList.size()).isEqualTo(5);

    }

    @Test
    public void 페이징() throws Exception {
        // given
        Member member = createMember();
        for (int i = 1; i <= 30; i++) {
            Post post = createPostEntity("제목" + i, "내용" + i, "카테고리", member);
            postRepository.save(post);
        }

        // when
        List<Post> posts = postRepository.findPage(0, PostConst.POST_LIMIT);

        // then
        assertThat(posts.size()).isEqualTo(10);
        assertThat(posts.get(0).getTitle()).isEqualTo("제목30");
        assertThat(posts.get(0).getContent()).isEqualTo("내용30");

    }

    @Test
    public void 전체_페이지_카운트() throws Exception {
        // given
        Member member = createMember();
        int countPage = 59;
        for (int i = 1; i <= 59; i++) {
            Post post = createPostEntity("제목" + i, "내용" + i, "카테고리", member);
            postRepository.save(post);
        }

        // when
        Long resultCount = postRepository.countPost();

        // then
        assertThat(resultCount).isEqualTo(countPage);

    }



    @Test
    public void delete() throws Exception {
        // given
        Member member = createMember();
        Post post = createPostEntity(member);
        postRepository.save(post);

        Post findPost = postRepository.findById(post.getId()).get();

        // when
        postRepository.delete(findPost);

        // then
        Post deletePost = postRepository.findById(post.getId()).orElse(null);
        assertThat(deletePost).isNull();
    }

    private Member createMember() {
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        Member member = dto.toEntity();
        em.persist(member);
        return member;
    }

    private static Post createPostEntity(String title, String content, String category, Member member) {
        return PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .category(category)
                .build()
                .toEntity(member);
    }

    private static Post createPostEntity(Member member) {
        return PostSaveRequestDto.builder()
                .title("제목")
                .content("내용")
                .category("카테고리")
                .build()
                .toEntity(member);
    }
}