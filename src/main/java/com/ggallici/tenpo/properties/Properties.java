package com.ggallici.tenpo.properties;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class Properties<T> {
    static final String DEFAULT_SERVICE_KEY = "default";

    private final Map<String, T> services;

    protected T getOrDefault(String key) {
        return Optional.ofNullable(services.get(key))
                .orElseGet(() -> services.get(DEFAULT_SERVICE_KEY));
    }
}
