package com.ggallici.tenpo.properties;

import org.junit.Test;

import java.util.Map;

import static com.ggallici.tenpo.properties.Properties.DEFAULT_SERVICE_KEY;
import static org.assertj.core.api.Assertions.assertThat;

public class RestPropertiesTest {
    private RestProperties restProperties;

    @Test
    public void getRetries() {
        var serviceKey = "test_service"; var expected = 10L;

        var services = Map.of(
                serviceKey, new RestProperties.Service(expected)
        );

        restProperties = new RestProperties(services);

        var retrieved = restProperties.getRetries(serviceKey);

        assertThat(retrieved).isEqualTo(expected);
    }

    @Test
    public void getRetries_usingDefault() {
        var serviceKey = "test_service"; var expected = 10L;

        var services = Map.of(
                DEFAULT_SERVICE_KEY, new RestProperties.Service(expected)
        );

        restProperties = new RestProperties(services);

        var retrieved = restProperties.getRetries(serviceKey);

        assertThat(retrieved).isEqualTo(expected);
    }
}