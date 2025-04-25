package com.example.deliveryoutsourcing.global.error;

import com.example.deliveryoutsourcing.global.error.response.ExceptionStatus;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final ExceptionStatus exceptionStatus;

    public ApiException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}