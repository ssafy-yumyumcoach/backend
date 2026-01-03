package com.yumyumcoach.domain.auth.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RefreshTokenRedisRepository {

    private static final String KEY_PREFIX = "refresh:";

    private final StringRedisTemplate redisTemplate;

    public RefreshTokenRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String tokenHash, String email, long ttlSeconds) {
        String key = KEY_PREFIX + tokenHash;
        redisTemplate.opsForValue().set(key, email, Duration.ofSeconds(ttlSeconds));
    }

    public String findEmailByHash(String tokenHash) {
        String key = KEY_PREFIX + tokenHash;
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteByHash(String tokenHash) {
        String key = KEY_PREFIX + tokenHash;
        Boolean deleted = redisTemplate.delete(key);
        return Boolean.TRUE.equals(deleted);
    }
}