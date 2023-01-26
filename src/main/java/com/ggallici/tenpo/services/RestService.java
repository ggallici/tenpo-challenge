package com.ggallici.tenpo.services;

import com.ggallici.tenpo.properties.RestProperties;
import com.ggallici.tenpo.exceptions.RestServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RestService {
    private final WebClient restClient;
    private final RestProperties restProperties;

    public <T> T getObject(String service, String uri, Class<T> clazz) {
        return restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::parseError)
                .bodyToMono(clazz)

                .cache(getTtl(service))
                .retryWhen(getRetries(service))

                .block();
        //TODO: no está en la caché y realizó 3 intentos consecutivos ?
    }

    private Mono<RestServiceException> parseError(ClientResponse error) {
        return error.createException()
                .map(ex -> new RestServiceException("External service error", ex));
    }

    private Duration getTtl(String service) {
        return Duration.ofMinutes(restProperties.getTtl(service));
    }

    private RetrySpec getRetries(String service) {
        return Retry.maxInARow(restProperties.getRetries(service));
    }
}
