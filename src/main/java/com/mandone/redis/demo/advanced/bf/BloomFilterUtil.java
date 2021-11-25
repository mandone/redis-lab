package com.mandone.redis.demo.advanced.bf;


import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BloomFilterUtil {

    @Resource(name = "redissionClient")
    private RedissonClient redissonClient;

    public void add(String key, Object value) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(1000, 0.01);
        bloomFilter.add(value);
    }

    public long count(String key) {
        return redissonClient.getBloomFilter(key).count();
    }
}
