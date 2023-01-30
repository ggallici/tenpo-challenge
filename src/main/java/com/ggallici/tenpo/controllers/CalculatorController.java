package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.dtos.add.AddRequestDto;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.services.CalculatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class CalculatorController {
    private final CalculatorService calculatorService;
    private final CalculatorMapper calculatorMapper;

    @PostMapping("/calculator/adder")
    public ResponseEntity<AddResponseDto> add(@Valid @RequestBody AddRequestDto addRequestDto) {
        var result = calculatorService.add(addRequestDto.first(), addRequestDto.second());
        var addResponseDto = calculatorMapper.toAddResponseDto(result);
        return ResponseEntity.ok(addResponseDto);
    }
}
