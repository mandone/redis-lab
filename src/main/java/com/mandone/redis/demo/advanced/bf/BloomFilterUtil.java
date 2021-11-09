package com.mandone.redis.demo.advanced.bf;


import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BloomFilterUtil {

    @Autowired
    private RedissonClient redissonClient;

    public void add(String key,String value){
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(key);
        if(!bloomFilter.isExists()){
            bloomFilter.tryInit(1000,0.01);
            bloomFilter.add(value);
        }
    }
}