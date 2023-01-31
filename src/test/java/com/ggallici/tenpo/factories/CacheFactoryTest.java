package com.ggallici.tenpo.factories;

import com.ggallici.tenpo.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CacheFactoryTest {
    @Mock
    private CacheProperties cachePropertiesMock;

    private CacheFactory cacheFactory;

    @Before
    public void setUp() {
        this.cacheFactory = new CacheFactory(cachePropertiesMock);
    }

    @Test
    public void testBuildCache() {
        var service = "test_service"; var ttl = 1L;

        doReturn(ttl).when(cachePropertiesMock).getTtl(service);

        var retrieved = cacheFactory.buildCache(service);

        assertThat(retrieved)
                .usingRecursiveComparison()
                .isEqualTo(buildExpected(ttl));

        verify(cachePropertiesMock).getTtl(service);
    }

    private <K, V> Cache<K, V> buildExpected(long ttl) {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(ttl))
                .build();
    }
}
