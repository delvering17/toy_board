package com.delver.board.exception;

import com.delver.board.exception.code.PostExceptionCode;
import lombok.Getter;

@Getter
public class PostException extends CustomException {

    private final PostExceptionCode postExceptionCode;

    public PostException(PostExceptionCode postExceptionCode) {
        super(postExceptionCode);
        this.postExceptionCode = postExceptionCode;
    }
}
