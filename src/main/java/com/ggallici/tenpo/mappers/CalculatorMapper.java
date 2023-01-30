package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.models.Add;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorMapper {
    public AddResponseDto toAddResponseDto(BigDecimal value) {
        return new AddResponseDto(value);
    }

    public Add toModel(AddResponseDto result) {
        return new Add(result.value());
    }
}
