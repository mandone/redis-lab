package com.mandone.redis.demo.advanced.geohash;

import com.mandone.redis.core.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 举例计算,需要提供经纬度 geoadd,geodist
 */
@Component
public class DistanceCalculate {

    @Autowired
    private RedisService redisService;

    public String getKey(){
        return "geoHash->distance:";
    }

    public Long savePosition(String member,Double longitude,Double latitude){
        return redisService.geoAdd(getKey(),longitude,latitude,member);
    }

    public String getDistance(String x,String y){
        return redisService.geoDist(getKey(),x,y).toString();
    }
}
