package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.mappers.TransactionLogMapper;
import com.ggallici.tenpo.services.TransactionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TransactionLogController {
    private final TransactionLogService transactionLogService;
    private final TransactionLogMapper transactionLogMapper;

    @GetMapping("/transactions")
    public ResponseEntity<TransactionLogsResponseDto> findById() {
        var result = transactionLogService.findAll();
        var transactionLogResponseDto = transactionLogMapper.toTransactionLogsResponseDto(result);
        return ResponseEntity.ok(transactionLogResponseDto);
    }
}
