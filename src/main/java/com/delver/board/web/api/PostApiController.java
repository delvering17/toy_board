package com.delver.board.web.api;

import com.delver.board.domain.member.Member;
import com.delver.board.exception.binding.ErrorResult;
import com.delver.board.service.PostService;
import com.delver.board.web.SessionConst;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.web.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;
    private final MessageSource messageSource;

    @PostMapping("/api/post/save")
    public ResponseEntity save(@RequestBody @Validated PostSaveRequestDto dto, BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Long postId = postService.savePost(dto, member.getId());

        Result result = Result.builder().status(HttpStatus.OK).content(String.valueOf(postId)).build();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

}
