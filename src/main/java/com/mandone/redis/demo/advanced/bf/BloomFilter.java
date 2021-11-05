package com.mandone.redis.demo.advanced.bf;

import com.mandone.redis.core.RedisService;
import org.springframework.beans.factory.annotation.Autowired;

public class BloomFilter {

    @Autowired
    private RedisService redisService;
}
