package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.models.TransactionLog;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionLogMapper {
    public TransactionLogsResponseDto toTransactionLogsResponseDto(List<TransactionLog> transactions) {
        return new TransactionLogsResponseDto(
                transactions.stream()
                        .map(this::toDto)
                        .collect(toList())
        );
    }

    private TransactionLogResponseDto toDto(TransactionLog transactionLog) {
        return new TransactionLogResponseDto(transactionLog.getStatus().name(), transactionLog.getUri(), transactionLog.getResult());
    }
}
