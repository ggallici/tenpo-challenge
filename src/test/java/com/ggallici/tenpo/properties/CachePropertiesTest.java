package com.ggallici.tenpo.properties;

import org.junit.Test;

import java.util.Map;

import static com.ggallici.tenpo.properties.Properties.DEFAULT_SERVICE_KEY;
import static org.assertj.core.api.Assertions.assertThat;

public class CachePropertiesTest {
    private CacheProperties cacheProperties;

    @Test
    public void getTtl() {
        var serviceKey = "test_service"; var expected = 10L;

        var services = Map.of(
                serviceKey, new CacheProperties.Service(expected)
        );

        cacheProperties = new CacheProperties(services);

        var retrieved = cacheProperties.getTtl(serviceKey);

        assertThat(retrieved).isEqualTo(expected);
    }

    @Test
    public void getTtl_usingDefault() {
        var serviceKey = "test_service"; var expected = 10L;

        var services = Map.of(
                DEFAULT_SERVICE_KEY, new CacheProperties.Service(expected)
        );

        cacheProperties = new CacheProperties(services);

        var retrieved = cacheProperties.getTtl(serviceKey);

        assertThat(retrieved).isEqualTo(expected);
    }
}
