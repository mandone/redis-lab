package com.mandone.redis.demo.advanced.bit;

import com.mandone.redis.core.RedisService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * setbit,getbit,bitcount,对于大数据量的判重数据可以使用
 */
@Component
public class AccessLog {

    @Autowired
    private RedisService redisService;

    private String getKey(){
        return "accessLog" + new DateTime().toString("yyyy-MM-dd");
    }

    public void addRequest(Long uniqueId){
        redisService.setBit(getKey(),uniqueId,true);
    }

    public Long getAccessCount(){
        return redisService.bitCount(getKey());
    }
}
