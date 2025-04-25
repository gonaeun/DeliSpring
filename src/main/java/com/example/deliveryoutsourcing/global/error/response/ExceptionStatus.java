package com.example.deliveryoutsourcing.global.error.response;

public interface ExceptionStatus {

    int getCode();

    int getStatus();

    String getMessage();
}