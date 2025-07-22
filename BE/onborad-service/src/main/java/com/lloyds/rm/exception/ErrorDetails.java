package com.lloyds.rm.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private String errorType;
    private int statusCode;
}
