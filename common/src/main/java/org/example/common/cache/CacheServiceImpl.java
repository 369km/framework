package org.example.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Object get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void set(String key, String value, Long expireTime) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime,TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Boolean existKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }


    @Override
    public Long incrBy(String key, long increment) {
        return stringRedisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public void expire(String key, long expireTime, TimeUnit timeUnit) {
        stringRedisTemplate.expire(key, expireTime, timeUnit);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }
}
