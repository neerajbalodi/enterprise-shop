package com.enterpriseshop.order.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * Stores Idempotency-Key -> orderId in Redis so a retried create request
 * returns the original order instead of creating a duplicate.
 */
@Service
public class IdempotencyService {

    private static final Duration TTL = Duration.ofHours(24);
    private final StringRedisTemplate redis;

    public IdempotencyService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    /** @return true if this key was newly reserved; false if it already existed. */
    public boolean reserve(String key, String orderId) {
        Boolean ok = redis.opsForValue().setIfAbsent("idem:" + key, orderId, TTL);
        return Boolean.TRUE.equals(ok);
    }

    public String existingOrderId(String key) {
        return redis.opsForValue().get("idem:" + key);
    }
}
