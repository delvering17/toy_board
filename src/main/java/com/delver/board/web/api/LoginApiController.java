package com.delver.board.web.api;

import com.delver.board.domain.member.Member;
import com.delver.board.exception.ExceptionResponse;
import com.delver.board.exception.LoginException;
import com.delver.board.exception.binding.ErrorDetail;
import com.delver.board.exception.binding.ErrorResult;
import com.delver.board.service.LoginService;
import com.delver.board.web.SessionConst;
import com.delver.board.web.controller.dto.LoginRequestDto;
import com.delver.board.web.response.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginApiController {

    private final LoginService loginService;
    private final MessageSource messageSource;

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody @Validated LoginRequestDto dto,
                        BindingResult bindingResult,
                        HttpServletRequest request) {


        if (bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        Member member = loginService.login(dto);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        Result result = Result.builder().status(HttpStatus.OK).content("OK").build();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionResponse> loginExHandle(LoginException e) {
        log.error("[loginExHandle] ex", e);
        ExceptionResponse exceptionResponse = new ExceptionResponse(e.getLoginExceptionCode());
        return ResponseEntity
                .status(exceptionResponse.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(exceptionResponse);
    }


}
