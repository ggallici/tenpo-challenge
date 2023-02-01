package com.ggallici.tenpo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.exceptions.RequestQuotaExceededException;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.models.TransactionStatus;
import com.ggallici.tenpo.services.TransactionLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static com.ggallici.tenpo.models.TransactionStatus.ERROR;
import static com.ggallici.tenpo.models.TransactionStatus.OK;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpMethod.POST;

@RunWith(MockitoJUnitRunner.class)
public class TransactionLogFilterTest {
    @Mock
    private TransactionLogService transactionLogServiceMock;
    @Mock
    private CalculatorMapper calculatorMapperMock;
    @Mock
    private ObjectMapper objectMapperMock;
    @Mock
    private FilterChain chainMock;

    private TransactionLogFilter transactionLogFilter;

    @Before
    public void setUp() throws Exception {
        this.transactionLogFilter = new TransactionLogFilter(transactionLogServiceMock, calculatorMapperMock, objectMapperMock);
    }

    @Test
    public void testDoFilterInternal_ok() throws ServletException, IOException {
        var uri = "/test";

        var expectedEntity = new Add(BigDecimal.TEN);
        var expectedDto = new AddResponseDto(BigDecimal.TEN);

        var request = new MockHttpServletRequest(POST.name(), uri);
        var response = new MockHttpServletResponse();

        doReturn(expectedDto).when(objectMapperMock).readValue(anyString(), eq(AddResponseDto.class));
        doReturn(expectedEntity).when(calculatorMapperMock).toModel(expectedDto);

        transactionLogFilter.doFilterInternal(request, response, chainMock);

        verify(transactionLogServiceMock).save(refEq(new TransactionLog(OK, uri, expectedEntity)));
        verify(objectMapperMock).readValue(anyString(), eq(AddResponseDto.class));
        verify(calculatorMapperMock).toModel(expectedDto);
    }

    @Test
    public void testDoFilterInternal_error() throws ServletException, IOException {
        var uri = "/test";

        var request = new MockHttpServletRequest(POST.name(), uri);
        var response = new MockHttpServletResponse();

        var expected = new RuntimeException("test message");

        doThrow(expected).when(chainMock).doFilter(any(), any());

        var retrieved = catchException(() -> transactionLogFilter.doFilterInternal(request, response, chainMock));

        assertThat(retrieved).isEqualTo(expected);
        verify(transactionLogServiceMock).save(refEq(new TransactionLog(ERROR, uri, null)));
    }
}
