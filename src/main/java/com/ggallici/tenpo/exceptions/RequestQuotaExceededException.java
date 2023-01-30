package com.ggallici.tenpo.exceptions;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

public class RequestQuotaExceededException extends RuntimeException {
    private final HttpStatus httpStatus = TOO_MANY_REQUESTS;

    public RequestQuotaExceededException() {
        super("Request quota exceeded");
    }

    public int getStatusCode() {
        return httpStatus.value();
    }
}
