package com.ggallici.tenpo.services;

import com.ggallici.tenpo.services.percentage.PercentageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class CalculatorServiceTest {
//    @Mock
//    private PercentageService percentageServiceMock;
//
//    private final CalculatorService calculatorService = new CalculatorService(percentageServiceMock);
//
//    @Test
//    void add() {
//        var first = new BigDecimal(1);
//        var second = new BigDecimal(2);
//        var percentage = new BigDecimal(3);
//        var expected = new BigDecimal(4);
//
//        doTest(first, second, percentage, expected);
//    }
//
//    private void doTest(BigDecimal first, BigDecimal second, BigDecimal percentage, BigDecimal expected) {
//        doReturn(percentage).when(percentageServiceMock).getPercentage();
//
//        var retrieved = calculatorService.add(first, second);
//
//        assertThat(retrieved).isEqualTo(expected);
//        verify(percentageServiceMock).getPercentage();
//    }
}
