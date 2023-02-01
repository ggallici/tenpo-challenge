package com.ggallici.tenpo.beans;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class BucketBean {
    @Value("${custom.rate.limit.service.max-rpm}")
    private int maxRPM;

    @Bean
    public Bucket bucket() {
        var refill = Refill.intervally(maxRPM, Duration.ofMinutes(1));
        var limit = Bandwidth.classic(maxRPM, refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
