package com.delver.board.web.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Result {

    private HttpStatus status;
    private String content;

    @Builder
    public Result(HttpStatus status, String content) {
        this.status = status;
        this.content = content;
    }
}
