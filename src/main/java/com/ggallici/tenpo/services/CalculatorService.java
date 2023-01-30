package com.ggallici.tenpo.services;

import com.ggallici.tenpo.services.percentage.PercentageRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final PercentageRestService percentageRestService;

    public BigDecimal add(BigDecimal first, BigDecimal second) {
        return applySurcharge(first.add(second), percentageRestService.getPercentage().value());
    }

    private BigDecimal applySurcharge(BigDecimal amount, BigDecimal percentage) {
        var surcharge = percentage.divide(ONE_HUNDRED).multiply(amount);
        return amount.add(surcharge);
    }
}
