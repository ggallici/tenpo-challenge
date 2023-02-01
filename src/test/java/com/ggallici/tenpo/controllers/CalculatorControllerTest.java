package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.add.AddRequestDto;
import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.exceptions.RestServiceException;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.services.CalculatorService;
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
public class CalculatorControllerTest {
    @Mock
    private CalculatorService calculatorServiceMock;
    @Mock
    private CalculatorMapper calculatorMapperMock;

    private CalculatorController calculatorController;

    @Before
    public void setUp() {
        this.calculatorController = new CalculatorController(calculatorServiceMock, calculatorMapperMock);
    }

    @Test
    public void testAdd_ok() {
        var error = false; var requestDto = new AddRequestDto(BigDecimal.ONE, BigDecimal.ZERO);

        var result = BigDecimal.TEN;
        var expected = new AddResponseDto(result);

        doReturn(result).when(calculatorServiceMock).add(requestDto.first(), requestDto.second(), error);
        doReturn(expected).when(calculatorMapperMock).toResponseDto(result);

        var retrieved = calculatorController.add(requestDto, error);

        assertThat(retrieved.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(retrieved.getBody()).isEqualTo(expected);

        verify(calculatorServiceMock).add(requestDto.first(), requestDto.second(), error);
        verify(calculatorMapperMock).toResponseDto(result);
    }

    @Test
    public void testAdd_error() {
        var error = true; var requestDto = new AddRequestDto(BigDecimal.ONE, BigDecimal.ZERO);

        var expected = new RestServiceException("test", HttpStatus.SERVICE_UNAVAILABLE, null);

        doThrow(expected).when(calculatorServiceMock).add(requestDto.first(), requestDto.second(), error);

        var retrieved = catchException(() -> calculatorController.add(requestDto, error));

        assertThat(retrieved).isEqualTo(expected);

        verify(calculatorServiceMock).add(requestDto.first(), requestDto.second(), error);
        verify(calculatorMapperMock, never()).toResponseDto(any(BigDecimal.class));
    }
}
