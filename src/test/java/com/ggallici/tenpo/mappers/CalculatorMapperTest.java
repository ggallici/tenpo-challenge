package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorMapperTest {
    private final CalculatorMapper calculatorMapper = new CalculatorMapper();

    @Test
    void toAddResponseDto() {
        BigDecimal result = BigDecimal.ONE;

        var retrieved = calculatorMapper.toAddResponseDto(result);

        assertThat(retrieved).isEqualTo(new AddResponseDto(result));
    }
}
