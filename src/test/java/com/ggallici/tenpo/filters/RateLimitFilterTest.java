package com.ggallici.tenpo.filters;

import com.ggallici.tenpo.exceptions.RequestQuotaExceededException;
import com.ggallici.tenpo.services.wrappers.RateLimitService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RateLimitFilterTest {
    @Mock
    private RateLimitService rateLimitServiceMock;

    private RateLimitFilter rateLimitFilter;

    @Before
    public void setUp() throws Exception {
        this.rateLimitFilter = new RateLimitFilter(rateLimitServiceMock);
    }

    @Test
    public void testDoFilterInternal_ok() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var chain = new MockFilterChain();

        rateLimitFilter.doFilterInternal(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getErrorMessage()).isNull();

        verify(rateLimitServiceMock).consume();
    }

    @Test
    public void testDoFilterInternal_error() throws ServletException, IOException {
        doThrow(new RequestQuotaExceededException()).when(rateLimitServiceMock).consume();

        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var chain = new MockFilterChain();

        rateLimitFilter.doFilterInternal(request, response, chain);

        assertThat(response.getStatus()).isEqualTo(429);
        assertThat(response.getErrorMessage()).isEqualTo("Request quota exceeded");

        verify(rateLimitServiceMock).consume();
    }
}
