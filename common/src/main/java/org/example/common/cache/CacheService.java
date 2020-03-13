package org.example.common.cache;

import java.util.concurrent.TimeUnit;

public interface CacheService {
    Object get(String key);

    void set(String key, String value, Long expireTime);

    void set(String key, String value);

    Boolean existKey(String key);

    Long incrBy(String key, long increment);

    void expire(String key, long expireTime, TimeUnit timeUnit);

    void remove(String key);
}
