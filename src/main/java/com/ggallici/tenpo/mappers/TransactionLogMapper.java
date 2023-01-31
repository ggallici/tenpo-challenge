package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.models.TransactionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TransactionLogMapper {
    private final CalculatorMapper calculatorMapper;

    public TransactionLogsResponseDto toResponseDto(List<TransactionLog> transactions) {
        return new TransactionLogsResponseDto(
                transactions.stream()
                        .map(this::toDto)
                        .collect(toList())
        );
    }

    private TransactionLogResponseDto toDto(TransactionLog transactionLog) {
        return new TransactionLogResponseDto(
                transactionLog.getStatus().name(),
                transactionLog.getUri(),
                calculatorMapper.toResponseDto(transactionLog.getResult())
        );
    }
}
