package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.mappers.TransactionLogMapper;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.services.TransactionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionLogController {
    private final TransactionLogService transactionLogService;
    private final TransactionLogMapper transactionLogMapper;

    @GetMapping("/transactions")
    public TransactionLogResponseDto findById() {
        var result = transactionLogService.findAll();
        return transactionLogMapper.toTransactionLogResponseDto(result);
    }
}
