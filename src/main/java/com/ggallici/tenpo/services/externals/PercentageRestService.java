package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.services.wrappers.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageRestService {
    static final String SERVICE = "percentage";
    static final String OK_URI = "https://percentage-service.free.beeceptor.com/ok";
    static final String ERROR_URI = "https://percentage-service.free.beeceptor.com/error";

    private final RestService restService;

    public PercentageResponseDto get(boolean error) {
        var uri = error ? ERROR_URI : OK_URI;
        return restService.getObject(SERVICE, uri, PercentageResponseDto.class);
    }
}
