package com.ggallici.tenpo.services;

import com.ggallici.tenpo.services.externals.PercentageRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    private final PercentageRestService percentageRestService;

    public BigDecimal add(BigDecimal first, BigDecimal second, boolean error) {
        return applySurcharge(first.add(second), percentageRestService.getPercentage(error).value());
    }

    private BigDecimal applySurcharge(BigDecimal amount, BigDecimal percentage) {
        var surcharge = percentage.divide(ONE_HUNDRED).multiply(amount);
        return amount.add(surcharge);
    }
}
