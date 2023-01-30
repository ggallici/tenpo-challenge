package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.transactionLog.TransactionLogDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.models.TransactionLog;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class TransactionLogMapper {
    public TransactionLogResponseDto toTransactionLogResponseDto(List<TransactionLog> transactionLogs) {
        return new TransactionLogResponseDto(
                transactionLogs.stream()
                        .map(this::toDto)
                        .collect(toList())
        );
    }

    private TransactionLogDto toDto(TransactionLog transactionLog) {
        return new TransactionLogDto(transactionLog.getStatus().name(), transactionLog.getUri(), transactionLog.getResult());
    }
}
