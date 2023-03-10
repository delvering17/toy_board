package com.delver.board.web.api;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.domain.member.Role;
import com.delver.board.domain.post.Post;
import com.delver.board.domain.post.PostRepository;
import com.delver.board.service.MemberService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.web.controller.dto.PostUpdateRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PostApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PostRepository postRepository;

    private Member member;
    private MockHttpSession mockSession;

    @BeforeEach
    public void init() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockSession = new MockHttpSession();
        member = createMemberAndLogin();
    }

    @Test
    public void ?????????_??????() throws Exception {
        // given
        PostSaveRequestDto dto = PostSaveRequestDto.builder()
                .title("??????")
                .content("??????")
                .category("????????????")
                .build();

        // when
        ResultActions action = mvc.perform(post("http://localhost:8080/api/post/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
                .session(mockSession)
        );

        // then
        action.andExpect(status().isOk());
        List<Post> posts = postRepository.findAll();
        assertThat(posts.size()).isEqualTo(1);

    }

    @Test
    public void ?????????_??????() throws Exception {
        // given
        PostSaveRequestDto saveDto = PostSaveRequestDto.builder()
                .title("??????")
                .content("??????")
                .category("????????????")
                .build();
        Long postId = postRepository.save(saveDto.toEntity(member));

        PostUpdateRequestDto updateDto = PostUpdateRequestDto.builder()
                .title("????????? ??????")
                .content("????????? ??????")
                .category("????????? ????????????")
                .build();

        // when/
        mvc.perform(post("http://localhost:8080/api/post/" + postId + "/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updateDto))
                .session(mockSession)
        ).andExpect(status().isOk());

        // then
        Post post = postRepository.findById(postId).get();
        assertThat(post.getTitle()).isEqualTo(updateDto.getTitle());
        assertThat(post.getContent()).isEqualTo(updateDto.getContent());
        assertThat(post.getCategory()).isEqualTo(updateDto.getCategory());

    }

    @Test
    public void ?????????_??????() throws Exception {
        // given
        PostSaveRequestDto saveDto = PostSaveRequestDto.builder()
                .title("??????")
                .content("??????")
                .category("????????????")
                .build();
        Long postId = postRepository.save(saveDto.toEntity(member));

        // when/
        mvc.perform(post("http://localhost:8080/api/post/" + postId + "/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .session(mockSession)
        ).andExpect(status().isOk());

        // then
        Post post = postRepository.findById(postId).get();
        assertThat(post).isNull();
    }

    private Member createMemberAndLogin() throws Exception {
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .email("delvering17@gmail.com")
                .password("password")
                .passwordConfirm("password")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        mvc.perform(post("http://localhost:8080/api/member/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
        ).andExpect(status().isOk());
        mvc.perform(post("http://localhost:8080/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
                .session(mockSession)
        );
        Member member = memberService.findByEmail("delvering17@gmail.com");
        return member;
    }

}
