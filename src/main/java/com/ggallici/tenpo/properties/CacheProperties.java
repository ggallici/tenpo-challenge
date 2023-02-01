package com.ggallici.tenpo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("custom.cache.service")
public class CacheProperties extends Properties<CacheProperties.Service> {
    public CacheProperties(Map<String, Service> services) {
        super(services);
    }

    public long getTtl(String key) {
        return getOrDefault(key).ttl();
    }

    record Service(long ttl) { }
}
