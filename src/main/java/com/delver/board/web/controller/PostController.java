package com.delver.board.web.controller;

import com.delver.board.domain.post.Post;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/post/save")
    public String save(Model model) {

        model.addAttribute("postSaveForm", PostSaveRequestDto.builder().build());

        return "post/postSaveForm";
    }

    @GetMapping("/post/{postId}")
    public String detail(@PathVariable Long postId, Model model) {

        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "post/postDetail";
    }


}
