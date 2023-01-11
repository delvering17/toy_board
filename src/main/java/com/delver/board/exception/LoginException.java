package com.delver.board.exception;

import com.delver.board.exception.code.LoginExceptionCode;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {

    private final LoginExceptionCode loginExceptionCode;

    public LoginException(LoginExceptionCode loginExceptionCode) {
        super(loginExceptionCode.getMessage());
        this.loginExceptionCode = loginExceptionCode;
    }
}
