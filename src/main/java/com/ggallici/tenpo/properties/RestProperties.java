package com.ggallici.tenpo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("custom.rest-service")
public class RestProperties extends Properties<RestProperties.Service> {
    public RestProperties(Map<String, Service> services) {
        super(services);
    }

    public long getRetries(String key) {
        return getOrDefault(key).retries();
    }

    record Service(long retries) { }
}
