package com.ggallici.tenpo.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggallici.tenpo.dtos.add.AddResponseDto;
import com.ggallici.tenpo.mappers.CalculatorMapper;
import com.ggallici.tenpo.models.Add;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.models.TransactionStatus;
import com.ggallici.tenpo.services.TransactionLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static com.ggallici.tenpo.models.TransactionStatus.ERROR;
import static com.ggallici.tenpo.models.TransactionStatus.OK;

@Order(2)
@WebFilter(urlPatterns = "/calculator/adder")
@RequiredArgsConstructor
public class TransactionLogFilter extends OncePerRequestFilter {
    private final TransactionLogService transactionLogService;
    private final CalculatorMapper calculatorMapper;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        var wrappedRequest = new ContentCachingRequestWrapper(request);
        var wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
            logTransaction(OK, wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            logTransaction(ERROR, wrappedRequest, null);
            throw e;
        }
    }

    @Async
    void logTransaction(TransactionStatus status, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        try {
            var transactionLog = new TransactionLog(status, getUri(request), getResult(response));
            transactionLogService.save(transactionLog);
        } catch (Exception ignored) {

        }
    }

    private String getUri(ContentCachingRequestWrapper request) {
        return request.getRequestURI();
    }

    private Add getResult(ContentCachingResponseWrapper response) {
        return Optional.ofNullable(response)
                .map(this::mapToEntity)
                .orElse(null);
    }

    private Add mapToEntity(ContentCachingResponseWrapper response) {
        try {
            var body = new String(response.getContentAsByteArray(), 0, response.getContentSize(), response.getCharacterEncoding());
            var dto = objectMapper.readValue(body, AddResponseDto.class);
            return calculatorMapper.toModel(dto);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            return null;
        } finally {
            try {
                response.copyBodyToResponse();
            } catch (IOException ignored) {

            }
        }
    }
}
