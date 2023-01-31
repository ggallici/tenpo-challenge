package com.ggallici.tenpo.mappers;

import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogResponseDto;
import com.ggallici.tenpo.dtos.transactionLog.TransactionLogsResponseDto;
import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.List;

import static com.ggallici.tenpo.models.TransactionStatus.ERROR;
import static com.ggallici.tenpo.models.TransactionStatus.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionLogMapperTest {
    @Mock
    private CalculatorMapper calculatorMapperMock;

    private TransactionLogMapper transactionLogMapper;

    @Before
    public void setUp() {
        this.transactionLogMapper = new TransactionLogMapper(calculatorMapperMock);
    }

    @Test
    public void testToResponseDto() {
        var uri = "/test";
        var addModel = new Add(BigDecimal.ONE);
        var addResponseDto = new AddResponseDto(BigDecimal.ONE);

        doReturn(addResponseDto).when(calculatorMapperMock).toResponseDto(addModel);

        var transactions = List.of(
                new TransactionLog(OK, uri, addModel),
                new TransactionLog(ERROR, uri, null)
        );

        var retrieved = transactionLogMapper.toResponseDto(transactions);

        assertThat(retrieved).isEqualTo(
                new TransactionLogsResponseDto(
                        List.of(
                                new TransactionLogResponseDto("OK", uri, addResponseDto),
                                new TransactionLogResponseDto("ERROR", uri, null)
                        )
                )
        );

        verify(calculatorMapperMock).toResponseDto(addModel);
    }
}
