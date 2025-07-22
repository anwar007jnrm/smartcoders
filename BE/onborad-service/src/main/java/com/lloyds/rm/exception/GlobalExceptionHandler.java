package com.lloyds.rm.exception;

import com.lloyds.rm.config.ErrorConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorConfig errorConfig;
    private final Map<String, ErrorDetails> errorDetailsMap;

    private final ErrorDetails defualtErrorDetails = ErrorDetails.builder()
            .errorMessage("An unexpected error occurred")
            .errorType("UnknownError")
            .statusCode(500)
            .build();

    public GlobalExceptionHandler(ErrorConfig errorConfig) {
        this.errorConfig = errorConfig;
        this.errorDetailsMap = errorConfig.getErrorDetails().stream()
                .collect(Collectors.toMap(ErrorDetails::getErrorCode, errorDetails -> errorDetails));
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        ErrorDetails errorDetails = errorDetailsMap.getOrDefault(ex.getErrorCode(), defualtErrorDetails);
        return ResponseEntity.status(errorDetails.getStatusCode())
                .body(ErrorResponse.builder()
                        .errorCode(ex.getErrorCode())
                        .errorMessage(errorDetails.getErrorMessage())
                        .errorType(errorDetails.getErrorType())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(defualtErrorDetails.getStatusCode())
                .body(ErrorResponse.builder()
                        .errorCode("9999")
                        .errorMessage(defualtErrorDetails.getErrorMessage())
                        .errorType(defualtErrorDetails.getErrorType())
                        .build());
    }
}