package com.ggallici.tenpo.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@ConfigurationProperties("custom.rest-service")
public class RestProperties {
    private static final String DEFAULT_SERVICE_KEY = "default";

    private final Map<String, Service> services;

    public long getTtl(String serviceKey) {
        return getServiceOrDefault(serviceKey).ttl();
    }

    public long getRetries(String serviceKey) {
        return getServiceOrDefault(serviceKey).retries();
    }

    private Service getServiceOrDefault(String serviceKey) {
        return Optional.ofNullable(services.get(serviceKey))
                .orElseGet(() -> services.get(DEFAULT_SERVICE_KEY));
    }

    record Service(long ttl, long retries) { }
}
