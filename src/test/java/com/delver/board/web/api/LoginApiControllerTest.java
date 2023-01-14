package com.delver.board.web.api;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.domain.member.Role;
import com.delver.board.web.constant.SessionConst;
import com.delver.board.web.controller.dto.LoginRequestDto;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class LoginApiControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberRepository memberRepository;
    private Member member;
    private MockHttpSession mockSession;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockSession = new MockHttpSession();
        member = createMember();

    }

    @AfterEach
    public void clear() {
        mockSession.invalidate();
        member = null;
    }


    @Test
    public void 로그인() throws Exception {
        // given
        LoginRequestDto dto = LoginRequestDto.builder()
                .email("delvering17@gmail.com")
                .password("password")
                .build();


        // when
        mvc.perform(post("http://localhost:8080/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
                .session(mockSession)
        ).andExpect(status().isOk());

        // then
        Member session_member = (Member) mockSession.getAttribute(SessionConst.LOGIN_MEMBER);
        assertThat(session_member).isEqualTo(member);

    }

    @Test
    public void 존재하지_않는_회원이면_HttpStatus_401() throws Exception {
        // given
        LoginRequestDto dto = LoginRequestDto.builder()
                .email("springgood@gmail.com")
                .password("password")
                .build();

        // when
        ResultActions action = mvc.perform(post("http://localhost:8080/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
                .session(mockSession)
        );

        // then
        action.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("code").value("LOGIN_001"))
                .andExpect(jsonPath("message").value("존재하지 않는 회원입니다."))
                .andDo(print());
    }

    @Test
    public void 비밀번호가_맞지_않으면_HttpStatus_400() throws Exception {
        // given
        LoginRequestDto dto = LoginRequestDto.builder()
                .email("delvering17@gmail.com")
                .password("notPassword")
                .build();

        // when
        ResultActions action = mvc.perform(post("http://localhost:8080/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
                .session(mockSession)
        );

        // then
        action.andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("code").value("LOGIN_002"))
                .andExpect(jsonPath("message").value("비밀번호가 맞지 않습니다."));

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
        memberRepository.save(member);
        return member;
    }



}