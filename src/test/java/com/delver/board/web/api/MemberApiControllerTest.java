package com.delver.board.web.api;

import com.delver.board.domain.member.JoinRoot;
import com.delver.board.domain.member.Member;
import com.delver.board.domain.member.MemberRepository;
import com.delver.board.domain.member.Role;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void 회원_가입() throws Exception {
        // given
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        // when
        mvc.perform(post("http://localhost:8080/api/member/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
        ).andExpect(status().isOk());


        // then
        Member member = memberRepository.findByUserName(dto.getUserName()).get();
        assertThat(member.getUserName()).isEqualTo(dto.getUserName());
        assertThat(member.getEmail()).isEqualTo(dto.getEmail());
        assertThat(member.getPassword()).isEqualTo(dto.getPassword());
        assertThat(member.getRole()).isEqualTo(dto.getRole());
        assertThat(member.getJoinRoot()).isEqualTo(dto.getJoinRoot());


    }

    @Test
    public void 비밀번호와_비밀번호확인_일치하지_않으면_HttpStatus_400() throws Exception {
        // given
        MemberSaveRequestDto dto = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("notPassword")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();
        
        // when
        ResultActions action = mvc.perform(post("http://localhost:8080/api/member/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto))
        );

        // then
        action.andExpect(status().isBadRequest())
                .andExpect(jsonPath("status").value("BAD_REQUEST"))
                .andExpect(jsonPath("code").value("MEMBER_002"))
                .andExpect(jsonPath("message").value("비밀번호와 비밀번호 확인이 일치하지 않습니다."));

    }

    @Test
    public void 이미_가입된_이메일이_HttpStatus_409() throws Exception {
        // given
        MemberSaveRequestDto dto1 = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        memberRepository.save(dto1.toEntity());

        MemberSaveRequestDto dto2 = MemberSaveRequestDto.createLocalMember()
                .userName("delver")
                .password("password")
                .passwordConfirm("password")
                .email("delvering17@gmail.com")
                .role(Role.USER)
                .joinRoot(JoinRoot.LOCAL)
                .build();

        // when
        ResultActions action = mvc.perform(post("http://localhost:8080/api/member/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto2))
        );

        // then
        action.andExpect(status().isConflict())
                .andExpect(jsonPath("status").value("CONFLICT"))
                .andExpect(jsonPath("code").value("MEMBER_003"))
                .andExpect(jsonPath("message").value("중복된 이메일입니다."));
    }





}
