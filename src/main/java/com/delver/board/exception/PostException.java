package com.delver.board.exception;

import com.delver.board.exception.code.PostExceptionCode;

public class PostException extends RuntimeException {

    private final PostExceptionCode postExceptionCode;

    public PostException(PostExceptionCode postExceptionCode) {
        super(postExceptionCode.getMessage());
        this.postExceptionCode = postExceptionCode;
    }
}
