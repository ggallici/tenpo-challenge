package com.ggallici.tenpo.services.wrappers;

import com.ggallici.tenpo.exceptions.RequestQuotaExceededException;
import io.github.bucket4j.Bucket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

@RunWith(MockitoJUnitRunner.class)
public class RateLimitServiceTest {
    @Mock
    private Bucket bucketMock;

    private RateLimitService rateLimitService;

    @Before
    public void setUp() {
        this.rateLimitService = new RateLimitService(bucketMock);
    }

    @Test
    public void testConsume_ok() {
        doReturn(true).when(bucketMock).tryConsume(1);

        assertThatCode(() -> rateLimitService.consume()).doesNotThrowAnyException();
    }

    @Test
    public void testConsume_error() {
        doReturn(false).when(bucketMock).tryConsume(1);

        assertThatThrownBy(() -> rateLimitService.consume())
                .isInstanceOf(RequestQuotaExceededException.class)
                .hasFieldOrPropertyWithValue("httpStatus", TOO_MANY_REQUESTS)
                .hasMessage("Request quota exceeded");
    }
}
