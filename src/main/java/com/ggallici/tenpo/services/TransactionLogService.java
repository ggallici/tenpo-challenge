package com.ggallici.tenpo.services;

import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.repositories.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionLogService {
    private final TransactionLogRepository transactionLogRepository;

    public void save(TransactionLog transactionLog) {
        transactionLogRepository.save(transactionLog);
    }

    public List<TransactionLog> findAll() {
        return transactionLogRepository.findAll();
    }
}
