package com.mandone.redis.demo.list;

import com.mandone.redis.core.RedisService;
import org.springframework.stereotype.Component;

@Component
public class SecKillQueue {

    private final RedisService redisService;


    public SecKillQueue(RedisService redisService) {
        this.redisService = redisService;
    }


    public void saveRequest(String request){
        String queueName = "secKillQueue";
        redisService.lPush(queueName,request);
    }

    public String getRequest(){
        String queueName = "secKillQueue";
        return redisService.rPop(queueName);
    }
}
