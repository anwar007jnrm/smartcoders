package com.lloyds.rm.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public ServiceException(String errorCode) {
        this.errorCode = errorCode;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
    }
}