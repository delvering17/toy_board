package com.delver.board.utils;

import com.delver.board.exception.CustomException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.delver.board.utils.ApiUtils.*;

public class ResponseEntityUtils {

    public static ResponseEntity<?> createResponseEntity(Object body, HttpStatus status) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(body, headers, status);
    }

    public static ResponseEntity<?> createErrorResponseEntity(HttpStatus status, Throwable throwable) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(status, throwable), headers, status);
    }
    public static ResponseEntity<?> createErrorResponseEntity(CustomException exception) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(exception.getExceptionCode().getStatus(), exception), headers, exception.getExceptionCode().getStatus());
    }
}
