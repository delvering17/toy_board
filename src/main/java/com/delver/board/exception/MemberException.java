package com.delver.board.exception;

import com.delver.board.exception.code.MemberExceptionCode;
import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

    private final MemberExceptionCode memberExceptionCode;

    public MemberException(MemberExceptionCode memberExceptionCode) {
        super(memberExceptionCode.getMessage());
        this.memberExceptionCode = memberExceptionCode;
    }
}
