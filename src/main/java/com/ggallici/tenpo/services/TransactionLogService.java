package com.ggallici.tenpo.services;

import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.repositories.TransactionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionLogService {
    private final TransactionLogRepository transactionLogRepository;

    @Transactional
    public void save(TransactionLog transactionLog) {
        transactionLogRepository.save(transactionLog);
    }

    public List<TransactionLog> findAll(int page, int size) {
        var pageRequest = PageRequest.of(page, size);
        return transactionLogRepository.findAll(pageRequest).getContent();
    }
}
