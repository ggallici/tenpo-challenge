package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.models.Add;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorMapperTest {
    private CalculatorMapper calculatorMapper;

    @Before
    public void setUp() {
        this.calculatorMapper = new CalculatorMapper();
    }

    @Test
    public void testToResponseDto_fromValue() {
        var value = BigDecimal.ONE;

        var retrieved = calculatorMapper.toResponseDto(value);

        assertThat(retrieved).isEqualTo(new AddResponseDto(value));
    }

    @Test
    public void testToResponseDto_fromModel() {
        var value = BigDecimal.ONE;

        var retrieved = calculatorMapper.toResponseDto(new Add(value));

        assertThat(retrieved).isEqualTo(new AddResponseDto(value));
    }

    @Test
    public void testToEntity() {
        var value = BigDecimal.ONE;

        var retrieved = calculatorMapper.toEntity(new AddResponseDto(value));

        assertThat(retrieved)
                .usingRecursiveComparison()
                .isEqualTo(new Add(value));
    }
}
