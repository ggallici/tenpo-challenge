package com.ggallici.tenpo.properties;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@ConfigurationProperties("custom.rest-service")
public class RestProperties {
    private static final String DEFAULT_SERVICE_KEY = "default";

    private final Map<String, Service> services;

    public long getTtl(String key) {
        return getOrDefault(key).ttl();
    }

    public long getRetries(String key) {
        return getOrDefault(key).retries();
    }

    private Service getOrDefault(String key) {
        return Optional.ofNullable(services.get(key))
                .orElseGet(() -> services.get(DEFAULT_SERVICE_KEY));
    }

    record Service(long ttl, long retries) { }
}
