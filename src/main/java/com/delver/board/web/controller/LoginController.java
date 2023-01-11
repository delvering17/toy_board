package com.delver.board.web.controller;

import com.delver.board.web.SessionConst;
import com.delver.board.web.controller.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginRequestDto dto = new LoginRequestDto();
        model.addAttribute("loginForm", dto);
        return "login/loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }


}
