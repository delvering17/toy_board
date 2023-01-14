package com.delver.board.web.api;

import com.delver.board.domain.member.Member;
import com.delver.board.exception.binding.ErrorResult;
import com.delver.board.service.PostService;
import com.delver.board.web.constant.SessionConst;
import com.delver.board.web.controller.dto.PostSaveRequestDto;
import com.delver.board.web.controller.dto.PostUpdateRequestDto;
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
            return createResponseEntityOfErrorResult(bindingResult);
        }

        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

        Long postId = postService.savePost(dto, member.getId());

        return createResponseEntityOfResult(String.valueOf(postId));
    }

    @PostMapping("/api/post/{postId}/update")
    public ResponseEntity update(@PathVariable Long postId,
                                 @RequestBody @Validated PostUpdateRequestDto dto,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return createResponseEntityOfErrorResult(bindingResult);
        }

        postService.updatePost(postId, dto);

        return createResponseEntityOfResult(String.valueOf(postId));
    }

    @PostMapping("/api/post/{postId}/delete")
    public ResponseEntity delete(@PathVariable Long postId) {

        postService.deletePost(postId);

        return createResponseEntityOfResult("/");
    }

    private static ResponseEntity<Result> createResponseEntityOfResult(String content) {
        Result result = Result.builder().status(HttpStatus.OK).content(content).build();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    private ResponseEntity<ErrorResult> createResponseEntityOfErrorResult(BindingResult bindingResult) {
        ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

}
