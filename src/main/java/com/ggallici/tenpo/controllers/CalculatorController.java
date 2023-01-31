package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.dtos.add.AddRequestDto;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.services.CalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final CalculatorMapper calculatorMapper;

    @PostMapping("/calculator/adder")
    public ResponseEntity<AddResponseDto> add(@Valid @RequestBody AddRequestDto addRequestDto, @RequestParam boolean error) {
        var result = calculatorService.add(addRequestDto.first(), addRequestDto.second(), error);
        var addResponseDto = calculatorMapper.toResponseDto(result);
        return ResponseEntity.ok(addResponseDto);
    }
}
