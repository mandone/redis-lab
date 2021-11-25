package com.mandone.redis.demo.advanced.bit;

import com.mandone.redis.core.RedisService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * setbit,getbit,bitcount,对于大数据量的判重数据可以使用
 */
@Component
public class AccessLog {

    @Resource(name = "lettuceClusterServiceImpl")
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
