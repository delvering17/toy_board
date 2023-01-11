package com.delver.board.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public interface ExceptionCode {


    HttpStatus getStatus();

    String getCode();
    String getMessage();


}
