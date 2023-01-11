package com.delver.board.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberExceptionCode implements ExceptionCode {

    MEMBER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "MEMBER_001", "존재하지 않는 회원입니다."),
    MEMBER_INCONSISTENCY_PASSWORDCONFIRM(HttpStatus.BAD_REQUEST, "MEMBER_002", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    MEMBER_DUPLICATE_EMAIL(HttpStatus.CONFLICT, "MEMBER_003", "중복된 이메일입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberExceptionCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
