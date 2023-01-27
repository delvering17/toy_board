package com.delver.board.utils;

import com.delver.board.exception.CustomException;
import com.delver.board.exception.binding.ErrorResult;
import com.delver.board.exception.code.ExceptionCode;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.Locale;

public class ApiUtils {

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static ApiResult<?> error(HttpStatus status, Throwable throwable) {
        return new ApiResult<>(false, null, new ApiError(status, throwable));
    }

    public static ApiResult<?> bindingError(BindingResult bindingResult, MessageSource messageSource) {
        return new ApiResult<>(false, null, new ApiError(bindingResult, messageSource));
    }

    @Getter
    public static class ApiResult<T> {
        private boolean success;
        private T response;
        private ApiError error;

        public ApiResult(boolean success, T response, ApiError error) {
            this.success = success;
            this.response = response;
            this.error = error;
        }
    }

    @Getter
    public static class ApiError {
        private int status;
        private String message;

        private ErrorResult errorResult;

        ApiError(ExceptionCode exceptionCode) {
            this.status = exceptionCode.getStatus().value();
            this.message = exceptionCode.getMessage();
        }

        ApiError(BindingResult bindingResult, MessageSource messageSource) {
            errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
        }

        ApiError(HttpStatus status, Throwable throwable) {
            this.status = status.value();
            this.message = throwable.getMessage();
        }

        ApiError(CustomException customException) {
            ExceptionCode exceptionCode = customException.getExceptionCode();
            this.status = exceptionCode.getStatus().value();
            this.message = exceptionCode.getMessage();
        }

        ApiError(HttpStatus status, String message) {
            this.status = status.value();
            this.message = message;
        }
    }


}
