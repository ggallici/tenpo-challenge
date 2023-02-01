package com.ggallici.tenpo.controllers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.mappers.TransactionLogMapper;
import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.services.TransactionLogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static com.ggallici.tenpo.models.TransactionStatus.ERROR;
import static com.ggallici.tenpo.models.TransactionStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionLogControllerTest {
    @Mock
    private TransactionLogService transactionLogServiceMock;
    @Mock
    private TransactionLogMapper transactionLogMapperMock;

    private TransactionLogController transactionLogController;

    @Before
    public void setUp() {
        this.transactionLogController = new TransactionLogController(transactionLogServiceMock, transactionLogMapperMock);
    }

    @Test
    public void testFindAll_ok() {
        var page = 0; var size = 10;

        var uri = "/test"; var value = BigDecimal.ONE;

        var result = List.of(
                new TransactionLog(OK, uri, new Add(value)),
                new TransactionLog(ERROR, uri, null)
        );

        var expected = new TransactionLogsResponseDto(
                List.of(
                        new TransactionLogResponseDto("OK", uri, new AddResponseDto(value)),
                        new TransactionLogResponseDto("ERROR", uri, null)
                )
        );

        doReturn(result).when(transactionLogServiceMock).findAll(page, size);
        doReturn(expected).when(transactionLogMapperMock).toResponseDto(result);

        var retrieved = transactionLogController.findAll(page, size);

        assertThat(retrieved.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(retrieved.getBody()).isEqualTo(expected);

        verify(transactionLogServiceMock).findAll(page, size);
        verify(transactionLogMapperMock).toResponseDto(result);
    }

    @Test
    public void testFindAll_error() {
        var page = 0; var size = 10;

        var expected = new RuntimeException("test", null);

        doThrow(expected).when(transactionLogServiceMock).findAll(page, size);

        var retrieved = catchException(() -> transactionLogController.findAll(page, size));

        assertThat(retrieved).isEqualTo(expected);

        verify(transactionLogServiceMock).findAll(page, size);
        verify(transactionLogMapperMock, never()).toResponseDto(any());
    }
}
