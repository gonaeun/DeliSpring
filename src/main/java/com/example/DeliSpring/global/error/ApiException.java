package com.example.DeliSpring.global.error;

import com.example.DeliSpring.global.error.response.ExceptionStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public ApiException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}