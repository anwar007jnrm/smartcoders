package com.lloyds.onboard.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private String errorCode;
    private String errorMessage;
    private String errorType;
    @JsonIgnore
    private int statusCode;
}
