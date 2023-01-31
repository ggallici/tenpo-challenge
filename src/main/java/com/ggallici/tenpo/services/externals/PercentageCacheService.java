package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.factories.CacheFactory;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PercentageCacheService {
    private static final String SERVICE = "percentage";

    private final Cache<String, PercentageResponseDto> cache;

    public PercentageCacheService(CacheFactory cacheFactory) {
        this.cache = cacheFactory.buildCache(SERVICE);
    }

    public Optional<PercentageResponseDto> get(String key) {
        return Optional.ofNullable(this.cache.getIfPresent(key));
    }

    public void put(String key, PercentageResponseDto value) {
        this.cache.put(key, value);
    }
}
