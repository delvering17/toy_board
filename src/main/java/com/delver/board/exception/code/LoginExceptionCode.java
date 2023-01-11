package com.delver.board.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum LoginExceptionCode implements ExceptionCode {

    LOGIN_NOTFOUND_MEMBER(HttpStatus.UNAUTHORIZED, "LOGIN_001", "존재하지 않는 회원입니다."),
    LOGIN_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "LOGIN_002", "비밀번호가 맞지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    LoginExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
