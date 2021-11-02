package com.mandone.redis;

import com.mandone.redis.basic.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisService redisService;

    @Test
    public void get(){
        Object aaa = redisService.get("Hello");
        System.out.println(aaa);
    }

    @Test
    public void set(){
        Object aaa = redisService.set("Hello","Mandone");
        System.out.println(aaa);
    }
}
