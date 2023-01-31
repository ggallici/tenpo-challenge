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
    @Value("${custom.rate-limit-service.max-rpm}")
    private int maxRPM;

    @Bean
    public Bucket bucket() {

        /*
            var refill = Refill.greedy(1, Duration.ofSeconds(20));
            var other = Bandwidth.classic(maxRPM, refill);
        **/

        var limit = Bandwidth.simple(maxRPM, Duration.ofMinutes(1));
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
