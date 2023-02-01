package com.ggallici.tenpo.services;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.exceptions.RestServiceException;
import com.ggallici.tenpo.services.externals.PercentageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorServiceTest {
    @Mock
    private PercentageService percentageServiceMock;

    private CalculatorService calculatorService;

    @Before
    public void setUp() {
        this.calculatorService = new CalculatorService(percentageServiceMock);
    }

    @Test
    public void testAdd_applySurchargeOnAmount() {
        doHappyTestCase("10", "10", "100", "40.00");
    }

    @Test
    public void testAdd_applySurchargeOnZeroAmount() {
        doHappyTestCase("0", "0", "100", "0.00");
    }

    @Test
    public void testAdd_applyZeroSurchargeOnAmount() {
        doHappyTestCase("10", "10", "0", "20.00");
    }

    @Test
    public void testAdd_applySurchargeOnNegativeAmount() {
        doHappyTestCase("10", "-20", "100", "-20.00");
    }

    @Test
    public void testAdd_applyNegativeSurchargeOnAmount() {
        doHappyTestCase("10", "10", "-50", "10.00");
    }

    @Test
    public void testAdd_applyRoundedUpSurchargeOnAmount() {
        doHappyTestCase("10", "10", "99.5", "40.00");
    }

    @Test
    public void testAdd_applyNonRoundedUpSurchargeOnAmount() {
        doHappyTestCase("10", "10", "99.49999", "39.80");
    }

    @Test
    public void testAdd_error() {
        var expected = new RestServiceException("test", HttpStatus.SERVICE_UNAVAILABLE, null);

        doThrow(expected).when(percentageServiceMock).getPercentage(true);

        var retrieved = catchException(() -> calculatorService.add(BigDecimal.ONE, BigDecimal.ZERO, true));

        assertThat(retrieved).isEqualTo(expected);
        verify(percentageServiceMock).getPercentage(true);
    }

    private void doHappyTestCase(String first, String second, String percentage, String expected) {
        doReturn(new PercentageResponseDto(new BigDecimal(percentage))).when(percentageServiceMock).getPercentage(false);

        var retrieved = calculatorService.add(new BigDecimal(first), new BigDecimal(second), false);

        assertThat(retrieved).isEqualTo(new BigDecimal(expected));
        verify(percentageServiceMock).getPercentage(false);
    }
}
