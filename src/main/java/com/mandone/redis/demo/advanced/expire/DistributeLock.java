package com.mandone.redis.demo.advanced.expire;

import com.mandone.redis.core.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 分布式锁
 */
@Component
public class DistributeLock {

    @Autowired
    private RedisService redisService;

    /**
     * 不支持可重入
     * @param key: key
     * @param value: value
     * @param expire : 设置过期时间
     * @return lock结果
     */
    public boolean lock(String key, String value, long expire) {
        Boolean lockResult = redisService.setNx(key, value);
        redisService.expire(key, expire);
        return lockResult;
    }
}
