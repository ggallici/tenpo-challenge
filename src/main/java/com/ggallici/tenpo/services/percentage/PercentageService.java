package com.ggallici.tenpo.services.percentage;

import com.ggallici.tenpo.exceptions.NoPercentageException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PercentageService {
//    private final PercentageRestService percentageRestService;
//    private final Cache percentageCacheService;
//
//    public BigDecimal getPercentage() {
//        return Optional.ofNullable(percentageCacheService.get("", BigDecimal.class))
//                .orElseGet(percentageRestService::getPercentage);
//    }
}
