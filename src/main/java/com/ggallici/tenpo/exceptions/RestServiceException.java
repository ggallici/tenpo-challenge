package com.ggallici.tenpo.exceptions;

import org.springframework.web.reactive.function.client.WebClientResponseException;

public class RestServiceException extends RuntimeException {
    public RestServiceException(String message, WebClientResponseException cause) {
        super(message, cause);
    }
}
