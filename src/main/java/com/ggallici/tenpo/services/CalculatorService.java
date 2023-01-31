package com.ggallici.tenpo.services;

import com.ggallici.tenpo.services.externals.PercentageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

@Service
@RequiredArgsConstructor
public class CalculatorService {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    private static final int SCALE = 2;

    private final PercentageService percentageService;

    public BigDecimal add(BigDecimal first, BigDecimal second, boolean error) {
        var amount = first.add(second);
        var percentage = percentageService.getPercentage(error).value();
        return applySurcharge(amount, percentage);
    }

    private BigDecimal applySurcharge(BigDecimal amount, BigDecimal percentage) {
        var surcharge = percentage.divide(ONE_HUNDRED, SCALE, HALF_UP).multiply(amount);
        return amount.add(surcharge);
    }
}
