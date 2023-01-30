package com.ggallici.tenpo.services.wrappers;

import com.ggallici.tenpo.exceptions.RequestQuotaExceededException;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimitService {
    private final Bucket bucket;

    public void consume() {
        if (!bucket.tryConsume(1)) {
            throw new RequestQuotaExceededException();
        }
    }
}
