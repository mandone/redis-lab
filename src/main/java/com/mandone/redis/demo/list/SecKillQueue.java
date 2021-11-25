package com.mandone.redis.demo.list;

import com.mandone.redis.core.RedisService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SecKillQueue {

    @Resource(name = "lettuceClusterServiceImpl")
    private  RedisService redisService;

    public void saveRequest(String request){
        String queueName = "secKillQueue";
        redisService.lPush(queueName,request);
    }

    public String getRequest(){
        String queueName = "secKillQueue";
        return redisService.rPop(queueName);
    }
}
