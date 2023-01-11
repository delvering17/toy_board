package com.delver.board.web.controller;

import com.delver.board.domain.member.MemberRepository;
import com.delver.board.service.MemberService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/save")
    public String signInForm(Model model) {
        model.addAttribute("memberSignInForm", MemberSaveRequestDto.createLocalMember().build());
        return "member/memberSignInForm";
    }



}
