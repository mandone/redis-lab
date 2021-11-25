package com.mandone.redis.core;

import org.springframework.data.geo.Distance;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service(value = "lettuceClusterServiceImpl")
public class RedisClusterServiceImpl implements RedisService{

    @Resource(name = "lettuceClusterTemplate")
    private RedisTemplate<String, Object> redisClusterTemplate;

    @Override
    public boolean expire(String key, long time) {
        return false;
    }

    @Override
    public Long getExpire(String key) {
        return null;
    }

    @Override
    public Boolean hasKey(String key) {
        return null;
    }

    @Override
    public void del(String... key) {

    }

    @Override
    public Object get(String key) {
        return redisClusterTemplate.opsForValue().get(key);
    }

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public boolean mset(Map<String, Object> value) {
        return false;
    }

    @Override
    public boolean multiSetIfAbsent(Map<String, Object> value) {
        return false;
    }

    @Override
    public List<?> mget(Set<String> keys) {
        return null;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        return false;
    }

    @Override
    public Integer append(String key, String value) {
        return null;
    }

    @Override
    public Long getLength(String key) {
        return null;
    }

    @Override
    public String getRange(String key, long start, long end) {
        return null;
    }

    @Override
    public Long incr(String key, long delta) {
        return null;
    }

    @Override
    public Long decr(String key, long delta) {
        return null;
    }

    @Override
    public Object hget(String key, String item) {
        return null;
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return null;
    }

    @Override
    public boolean hmset(String key, Map<?, ?> map) {
        return false;
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        return false;
    }

    @Override
    public boolean hset(String key, String item, Object value) {
        return false;
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        return false;
    }

    @Override
    public void hdel(String key, Object... item) {

    }

    @Override
    public boolean hHasKey(String key, String item) {
        return false;
    }

    @Override
    public double hincr(String key, String item, double by) {
        return 0;
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return 0;
    }

    @Override
    public Set<Object> sGet(String key) {
        return null;
    }

    @Override
    public Boolean sHasKey(String key, Object value) {
        return null;
    }

    @Override
    public Long sSet(String key, Object... values) {
        return null;
    }

    @Override
    public Long sSetAndTime(String key, long time, Object... values) {
        return null;
    }

    @Override
    public Long sGetSetSize(String key) {
        return null;
    }

    @Override
    public Long setRemove(String key, Object... values) {
        return null;
    }

    @Override
    public List<Object> lGet(String key, long start, long end) {
        return null;
    }

    @Override
    public Long lGetListSize(String key) {
        return null;
    }

    @Override
    public Object lGetIndex(String key, long index) {
        return null;
    }

    @Override
    public boolean lSet(String key, Object value) {
        return false;
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        return false;
    }

    @Override
    public boolean lSetAll(String key, List<Object> value) {
        return false;
    }

    @Override
    public boolean lSetAll(String key, List<Object> value, long time) {
        return false;
    }

    @Override
    public boolean rSet(String key, Object value) {
        return false;
    }

    @Override
    public boolean rSet(String key, Object value, long time) {
        return false;
    }

    @Override
    public boolean rSetAll(String key, List<Object> value) {
        return false;
    }

    @Override
    public boolean rSetAll(String key, List<Object> value, long time) {
        return false;
    }

    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        return false;
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        return null;
    }

    @Override
    public void rangeRemove(String key, Long stard, Long end) {

    }

    @Override
    public void setBit(String key, long offset, boolean value) {

    }

    @Override
    public Boolean getBit(String key, long offset) {
        return null;
    }

    @Override
    public Long bitCount(String key) {
        return null;
    }

    @Override
    public Long hincr(String bucket, String key, long delta) {
        return null;
    }

    @Override
    public Boolean hexists(String s, String title) {
        return null;
    }

    @Override
    public Map<String, String> hgetAll(String s) {
        return null;
    }

    @Override
    public void lPush(String key, String value) {

    }

    @Override
    public void rPush(String key, String value) {

    }

    @Override
    public String lPop(String key) {
        return null;
    }

    @Override
    public String rPop(String key) {
        return null;
    }

    @Override
    public Long geoAdd(String key, Double longitude, Double latitude, String member) {
        return null;
    }

    @Override
    public Distance geoDist(String key, String member1, String member2) {
        return null;
    }

    @Override
    public Boolean setNx(String key, String value) {
        return null;
    }
}
