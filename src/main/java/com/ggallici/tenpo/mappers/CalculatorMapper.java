package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.models.Add;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorMapper {
    public AddResponseDto toResponseDto(BigDecimal value) {
        return new AddResponseDto(value);
    }

    public AddResponseDto toResponseDto(Add result) {
        return new AddResponseDto(result.getValue());
    }

    public Add toEntity(AddResponseDto result) {
        return new Add(result.value());
    }
}
