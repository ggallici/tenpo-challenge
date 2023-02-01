package com.ggallici.tenpo.services;

import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.repositories.TransactionLogRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static com.ggallici.tenpo.models.TransactionStatus.ERROR;
import static com.ggallici.tenpo.models.TransactionStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionLogServiceTest {
    @Mock
    private TransactionLogRepository transactionLogRepositoryMock;

    private TransactionLogService transactionLogService;

    @Before
    public void setUp() {
        this.transactionLogService = new TransactionLogService(transactionLogRepositoryMock);
    }

    @Test
    public void testSave() {
        var entity = new TransactionLog(ERROR, "/test", null);

        transactionLogService.save(entity);

        verify(transactionLogRepositoryMock).save(entity);
    }

    @Test
    public void testFindAll() {
        int page = 0; int size = 10;
        var pageRequest = PageRequest.of(page, size);

        var expected = List.of(
                new TransactionLog(ERROR, "/test", null),
                new TransactionLog(OK, "/test", new Add(BigDecimal.ONE))
        );

        doReturn(buildPage(expected, pageRequest)).when(transactionLogRepositoryMock).findAll(pageRequest);

        var retrieved = transactionLogService.findAll(page, size);

        assertThat(retrieved).isEqualTo(expected);

        verify(transactionLogRepositoryMock).findAll(pageRequest);
    }

    private Page<TransactionLog> buildPage(List<TransactionLog> transactions, PageRequest pageRequest) {
        return new PageImpl<>(transactions, pageRequest, transactions.size());
    }
}
