package com.delver.board.exception;

import com.delver.board.exception.code.ExceptionCode;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    private final ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
