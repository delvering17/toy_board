package com.delver.board.exception;

import com.delver.board.exception.code.LoginExceptionCode;
import lombok.Getter;

@Getter
public class LoginException extends CustomException {

    private final LoginExceptionCode loginExceptionCode;

    public LoginException(LoginExceptionCode loginExceptionCode) {
        super(loginExceptionCode);
        this.loginExceptionCode = loginExceptionCode;
    }
}
