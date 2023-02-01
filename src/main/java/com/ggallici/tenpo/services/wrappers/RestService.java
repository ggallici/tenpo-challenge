package com.ggallici.tenpo.services.wrappers;

import com.ggallici.tenpo.exceptions.RestServiceException;
import com.ggallici.tenpo.properties.RestProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.Retry.RetrySignal;
import reactor.util.retry.RetrySpec;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Service
public class RestService {
    private final WebClient restClient;
    private final RestProperties restProperties;

    public RestService(RestProperties restProperties) {
        this.restProperties = restProperties;
        this.restClient = WebClient.create();
    }

    public <T> T getObject(String service, String uri, Class<T> clazz) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(clazz)

                .retryWhen(getRetries(service)
                        .onRetryExhaustedThrow(this::wrapException))

                .block();
    }

    private RestServiceException wrapException(RetrySpec spec, RetrySignal signal) {
        return new RestServiceException(
                "External service error - Retries exhausted after %d attempts".formatted(signal.totalRetries() + 1),
                SERVICE_UNAVAILABLE, (WebClientResponseException) signal.failure()
        );
    }

    private RetrySpec getRetries(String service) {
        return Retry.maxInARow(restProperties.getRetries(service));
    }
}
