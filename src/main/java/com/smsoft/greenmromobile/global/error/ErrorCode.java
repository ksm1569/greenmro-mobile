package com.smsoft.greenmromobile.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_QUANTITY(HttpStatus.BAD_REQUEST, "P-001", "수량은 1이상만 입력가능합니다."),
    EXCEEDED_QUANTITY(HttpStatus.BAD_REQUEST, "P-002", "재고수량을 초과하였습니다.")
    ;

    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;
}
