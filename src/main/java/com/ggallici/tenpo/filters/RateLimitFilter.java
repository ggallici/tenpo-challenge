package com.ggallici.tenpo.filters;

import com.ggallici.tenpo.exceptions.RequestQuotaExceededException;
import com.ggallici.tenpo.services.wrappers.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(1)
@Component
@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {
    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            rateLimitService.consume();
            chain.doFilter(request, response);
        } catch (RequestQuotaExceededException e) {
            response.sendError(e.getHttpStatusCode(), e.getMessage());
        }
    }
}
