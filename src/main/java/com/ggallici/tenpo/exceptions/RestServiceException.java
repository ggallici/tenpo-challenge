package com.ggallici.tenpo.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Getter
public class RestServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RestServiceException(String message, HttpStatus httpStatus, WebClientResponseException cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
