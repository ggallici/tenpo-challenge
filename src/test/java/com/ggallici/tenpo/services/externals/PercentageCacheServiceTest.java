package com.ggallici.tenpo.services.externals;

import com.ggallici.tenpo.dtos.percentage.PercentageResponseDto;
import com.ggallici.tenpo.factories.CacheFactory;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PercentageCacheServiceTest {
    @Mock
    private CacheFactory cacheFactoryMock;
    @Mock
    private Cache<String, PercentageResponseDto> cacheMock;

    private PercentageCacheService percentageCacheService;

    @Before
    public void setUp() {
        doReturn(cacheMock).when(cacheFactoryMock).buildCache("percentage");

        this.percentageCacheService = new PercentageCacheService(cacheFactoryMock);
    }

    @Test
    public void testGet_present() {
        var key = "1"; var value = new PercentageResponseDto(BigDecimal.TEN);

        doReturn(value).when(cacheMock).getIfPresent(key);

        var retrieved = percentageCacheService.get(key);

        assertThat(retrieved).isEqualTo(Optional.of(value));
        verify(cacheMock).getIfPresent(key);
    }

    @Test
    public void testGet_empty() {
        var key = "1";

        var retrieved = percentageCacheService.get(key);

        assertThat(retrieved).isEqualTo(Optional.empty());
        verify(cacheMock).getIfPresent(key);
    }

    @Test
    public void testPut() {
        var key = "1"; var value = new PercentageResponseDto(BigDecimal.TEN);

        percentageCacheService.put(key, value);

        verify(cacheMock).put(key, value);
    }
}
