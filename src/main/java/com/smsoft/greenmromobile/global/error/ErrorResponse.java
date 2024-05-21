package com.smsoft.greenmromobile.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {
    private String errorCode;
    private String message;
}
