package com.delver.board.web.controller;

import com.delver.board.domain.member.Member;
import com.delver.board.service.PostService;
import com.delver.board.web.constant.PostConst;
import com.delver.board.web.constant.SessionConst;
import com.delver.board.web.controller.dto.PostResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(@RequestParam(name = "page", defaultValue = "1") int page,
                       Model model,
                       HttpServletRequest request) {

        List<PostResponseDto> postList = postService.findPage(page, PostConst.POST_LIMIT);
        Long countPage = postService.countPage();

        model.addAttribute("postList", postList);
        model.addAttribute("page", page);
        model.addAttribute("countPage", countPage);
        model.addAttribute("postLimit", PostConst.POST_LIMIT);

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (member == null) {
            return "home";
        }

        model.addAttribute(SessionConst.LOGIN_MEMBER, member);
        return "home";
    }


}
