package com.delver.board.web.controller;

import com.delver.board.web.controller.dto.PostResponseDto;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.service.PostService;
import com.delver.board.web.controller.dto.PostUpdateForm;
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
    public String detail(@PathVariable Long postId, @RequestParam(defaultValue = "1") int page, Model model) {

        PostResponseDto dto = postService.findById(postId);
        model.addAttribute("post", dto);
        model.addAttribute("postId", postId);
        model.addAttribute("page", page);
        return "post/postDetail";
    }

    @GetMapping("/post/{postId}/update")
    public String update(@PathVariable Long postId, @RequestParam(defaultValue = "1") int page, Model model) {

        PostUpdateForm form = PostUpdateForm.builder()
                .dto(postService.findById(postId))
                .build();

        model.addAttribute("postUpdateForm", form);
        model.addAttribute("postId", postId);
        model.addAttribute("page", page);

        return "post/postUpdateForm";
    }


}
