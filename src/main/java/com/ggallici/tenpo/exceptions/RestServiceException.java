package com.ggallici.tenpo.exceptions;

public class RestServiceException extends RuntimeException {
    public RestServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
