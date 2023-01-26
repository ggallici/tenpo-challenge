package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculatorMapper {
    public AddResponseDto toAddResponseDto(BigDecimal result) {
        return new AddResponseDto(result);
    }
}
