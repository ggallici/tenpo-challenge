package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageService {
    static final String CACHE_KEY = "last_result";

    private final PercentageCacheService percentageCacheService;
    private final PercentageRestService percentageRestService;

    public PercentageResponseDto getPercentage(boolean error) {
        return percentageCacheService.get(CACHE_KEY)
                .orElseGet(() -> refreshCache(error));
    }

    private PercentageResponseDto refreshCache(boolean error) {
        var result = percentageRestService.get(error);
        percentageCacheService.put(CACHE_KEY, result);
        return result;
    }
}
