package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.services.TransactionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionLogController {
    private final TransactionLogService transactionLogService;

    @GetMapping("/transactions")
    public List<TransactionLog> findById() {
        return transactionLogService.findAll();
    }
}
