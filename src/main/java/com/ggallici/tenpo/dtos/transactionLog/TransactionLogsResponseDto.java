package com.ggallici.tenpo.dtos.transactionLog;

import java.util.List;

public record TransactionLogsResponseDto(List<TransactionLogResponseDto> transactions) { }
