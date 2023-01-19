package com.delver.board.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostExceptionCode implements ExceptionCode {

    POST_NOTFOUND(HttpStatus.UNAUTHORIZED, "POST_001", "존재하지 않는 게시물입니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

    PostExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
