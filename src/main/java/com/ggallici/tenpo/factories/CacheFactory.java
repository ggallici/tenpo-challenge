package com.ggallici.tenpo.factories;

import com.ggallici.tenpo.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CacheFactory {
    private final CacheProperties cacheProperties;

    public <K, V> Cache<K, V> buildCache(String service) {
        return Caffeine.newBuilder()
                .expireAfterWrite(getTtl(service))
                .build();
    }

    private Duration getTtl(String service) {
        return Duration.ofMinutes(cacheProperties.getTtl(service));
    }
}
