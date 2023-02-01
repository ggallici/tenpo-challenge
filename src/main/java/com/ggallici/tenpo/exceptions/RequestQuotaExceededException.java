package com.ggallici.tenpo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@Getter
public class RequestQuotaExceededException extends RuntimeException {
    private final HttpStatus httpStatus = TOO_MANY_REQUESTS;

    public RequestQuotaExceededException() {
        super("Request quota exceeded");
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
