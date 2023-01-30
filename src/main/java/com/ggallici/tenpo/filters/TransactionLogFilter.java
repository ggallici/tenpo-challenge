package com.ggallici.tenpo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggallici.tenpo.models.TransactionLog;
import com.ggallici.tenpo.models.TransactionStatus;
import com.ggallici.tenpo.services.TransactionLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TransactionLogFilter extends OncePerRequestFilter {
    private final TransactionLogService transactionLogService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        var wrappedRequest = new ContentCachingRequestWrapper(request);
        var wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            chain.doFilter(wrappedRequest, wrappedResponse);
            logTransaction(TransactionStatus.OK, wrappedRequest, wrappedResponse);
        } catch (Exception e) {
            logTransaction(TransactionStatus.ERROR, wrappedRequest, wrappedResponse);
        }
    }

    @Async
    void logTransaction(TransactionStatus status, ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        try {
            transactionLogService.save(new TransactionLog(status, getURI(request), getBody(response)));
        } catch (Exception ignored) {

        }
    }

    private String getURI(ContentCachingRequestWrapper request) {
        return request.getRequestURI();
    }

    private Object getBody(ContentCachingResponseWrapper response) throws IOException {
        try {
            var jsonBody = new String(response.getContentAsByteArray(), 0, response.getContentSize(), response.getCharacterEncoding());
            return objectMapper.readValue(jsonBody, Object.class);
        } finally {
            response.copyBodyToResponse();
        }
    }
}
