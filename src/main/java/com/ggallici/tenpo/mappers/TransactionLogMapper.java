package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TransactionLogMapper {
    private final CalculatorMapper calculatorMapper;

    public TransactionLogsResponseDto toResponseDto(List<TransactionLog> transactions) {
        return new TransactionLogsResponseDto(
                transactions.stream()
                        .map(this::maptoDto)
                        .collect(toList())
        );
    }

    private TransactionLogResponseDto maptoDto(TransactionLog transactionLog) {
        return new TransactionLogResponseDto(
                transactionLog.getStatus().name(), transactionLog.getUri(), maptoDto(transactionLog.getResult())
        );
    }

    private AddResponseDto maptoDto(Add result) {
        return Optional.ofNullable(result)
                .map(calculatorMapper::toResponseDto)
                .orElse(null);
    }
}
