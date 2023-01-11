package com.delver.board.web.api;

import com.delver.board.exception.ExceptionResponse;
import com.delver.board.exception.MemberException;
import com.delver.board.exception.binding.ErrorResult;
import com.delver.board.service.MemberService;
import com.delver.board.web.controller.dto.MemberSaveRequestDto;
import com.delver.board.web.response.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;
    private final MessageSource messageSource;

    @PostMapping("/api/member/save")
    public ResponseEntity signIn(@RequestBody @Validated MemberSaveRequestDto dto,
                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        memberService.save(dto);


        Result result = Result.builder().status(HttpStatus.OK).content("OK").build();
        return ResponseEntity.status(result.getStatus()).body(result);
    }

    @ResponseBody
    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ExceptionResponse> memberExHandler(MemberException e) {
        log.error("[memberExHandle] ex", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getMemberExceptionCode());
        return ResponseEntity
                .status(exceptionResponse.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }


}
