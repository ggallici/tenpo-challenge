package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.exceptions.RestServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ggallici.tenpo.services.externals.PercentageService.CACHE_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PercentageServiceTest {
    @Mock
    private PercentageCacheService percentageCacheServiceMock;
    @Mock
    private PercentageRestService percentageRestServiceMock;

    private PercentageService percentageService;

    @Before
    public void setUp() {
        this.percentageService = new PercentageService(percentageCacheServiceMock, percentageRestServiceMock);
    }

    @Test
    public void testGetPercentage_presentInCache() {
        var expected = new PercentageResponseDto(BigDecimal.TEN);

        doReturn(Optional.of(expected)).when(percentageCacheServiceMock).get(CACHE_KEY);

        var retrieved = percentageService.getPercentage(false);

        assertThat(retrieved).isEqualTo(expected);
        verify(percentageCacheServiceMock).get(CACHE_KEY);
        verify(percentageRestServiceMock, never()).get(false);
        verify(percentageCacheServiceMock, never()).put(CACHE_KEY, expected);
    }

    @Test
    public void testGetPercentage_presentInService() {
        var expected = new PercentageResponseDto(BigDecimal.TEN);

        doReturn(Optional.empty()).when(percentageCacheServiceMock).get(CACHE_KEY);
        doReturn(expected).when(percentageRestServiceMock).get(false);

        var retrieved = percentageService.getPercentage(false);

        assertThat(retrieved).isEqualTo(expected);
        verify(percentageCacheServiceMock).get(CACHE_KEY);
        verify(percentageRestServiceMock).get(false);
        verify(percentageCacheServiceMock).put(CACHE_KEY, expected);
    }

    @Test
    public void testGetPercentage_notPresent() {
        var expected = new RestServiceException("test", HttpStatus.SERVICE_UNAVAILABLE, null);

        doReturn(Optional.empty()).when(percentageCacheServiceMock).get(CACHE_KEY);
        doThrow(expected).when(percentageRestServiceMock).get(true);

        var retrieved = catchException(() -> percentageService.getPercentage(true));

        assertThat(retrieved).isEqualTo(expected);
        verify(percentageCacheServiceMock).get(CACHE_KEY);
        verify(percentageRestServiceMock).get(true);
        verify(percentageCacheServiceMock, never()).put(eq(CACHE_KEY), any());
    }
}
