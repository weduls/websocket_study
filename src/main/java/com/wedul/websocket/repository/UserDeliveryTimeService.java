package com.wedul.websocket.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDeliveryTimeService {

    private final RedisTemplate redisTemplate;

    public int getTime(String user, int defaultVal) {
        Object time = redisTemplate.opsForValue().get(user);
        if (null == time) {
            return defaultVal;
        }
        return (int) time;
    }

    public void updateTime(String user, int time) {
        redisTemplate.opsForValue().set(user, time);
    }

    public void deleteTime(String user) {
        redisTemplate.delete(user);
    }

}
