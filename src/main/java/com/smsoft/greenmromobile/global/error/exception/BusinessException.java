package com.smsoft.greenmromobile.global.error.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}