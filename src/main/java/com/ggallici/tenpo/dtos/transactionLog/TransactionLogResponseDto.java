package com.ggallici.tenpo.dtos.transactionLog;

import com.ggallici.tenpo.dtos.add.AddResponseDto;

public record TransactionLogResponseDto(String status, String uri, AddResponseDto result) { }
