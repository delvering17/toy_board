package com.delver.board.web.controller;

import com.delver.board.domain.member.Member;
import com.delver.board.domain.post.Post;
import com.delver.board.service.PostService;
import com.delver.board.web.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostService postService;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {

        List<Post> postList = postService.findAll();

        model.addAttribute("postList", postList);

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
