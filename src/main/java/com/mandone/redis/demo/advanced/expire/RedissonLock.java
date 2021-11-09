package com.mandone.redis.demo.advanced.expire;

import com.mandone.redis.core.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedissonLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLock.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 使用redission分布式锁
     * https://github.com/redisson/redisson/wiki/8.-Distributed-locks-and-synchronizers
     *
     * tryLock 非阻塞锁
     * @param key
     */
    public void tryLock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            boolean lockRes = lock.tryLock(5, 30, TimeUnit.SECONDS);
            if (lockRes) {
                System.out.println("get lock success!");
                //禁止业务逻辑中使用锁的key值
                System.out.println("start to do business");
                lock.tryLock(5, 30, TimeUnit.SECONDS);
                System.out.println("二次加锁,锁市场20s");
            } else {
                System.out.println("failed get locak");
            }
        } catch (Exception e) {
            System.out.println("failed get lock!");
            LOGGER.error("failed get lock!", e);
        }
    }

    /**
     * lock是阻塞锁，获取不到会一直阻塞住，等待锁释放
     * @param key
     */
    public void lock(String key) {
        RLock lock = redissonClient.getLock(key);
        try {
            long start = System.currentTimeMillis();
            lock.lock(20, TimeUnit.SECONDS);
            long end = System.currentTimeMillis();
            long cost = TimeUnit.MILLISECONDS.toSeconds(end - start);
            System.out.println("get lock success!,耗时 + " + cost + "s");
        } catch (Exception e) {
            System.out.println("failed get lock!");
            LOGGER.error("failed get lock!", e);
        }
    }
}
