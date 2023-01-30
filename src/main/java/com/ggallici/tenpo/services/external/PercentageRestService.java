package com.ggallici.tenpo.services.external;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.services.wrappers.RestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PercentageRestService {
    private static final String SERVICE = "percentage";
    private static final String URI = "https://mocki.io/v1/78fb1887-a682-4453-9871-1b46ec0d7153";

    private final RestService restService;

    public PercentageResponseDto getPercentage() {
        return restService.getObject(SERVICE, URI, PercentageResponseDto.class);
    }
}
