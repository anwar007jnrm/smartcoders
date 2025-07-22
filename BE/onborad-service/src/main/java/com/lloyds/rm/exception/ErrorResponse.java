package com.lloyds.rm.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private String errorType;

    public ErrorResponse(String errorCode, String errorMessage, String errorType) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
}