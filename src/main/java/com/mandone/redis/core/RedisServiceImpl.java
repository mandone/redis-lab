package com.mandone.redis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    private final RedisTemplate<String, Object> redisTemplate;


    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            LOGGER.error("method hasKey execute failed", e);
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }
    //################################################String###########################################################//

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean mset(Map<String, Object> value) {
        try {
            redisTemplate.opsForValue().multiSet(value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean multiSetIfAbsent(Map<String, Object> value) {
        try {
            redisTemplate.opsForValue().multiSetIfAbsent(value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }


    @Override
    public List<?> mget(Set<String> keys) {
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public Integer append(String key, String value) {
        try {
            return redisTemplate.opsForValue().append(key, value);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0;
        }
    }

    @Override
    public Long getLength(String key) {
        try {
            return redisTemplate.execute((RedisCallback<Long>) connection -> connection.strLen(key.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public String getRange(String key, long start, long end) {
        try {
            return redisTemplate.execute((RedisCallback<String>) connection -> new String(Objects.requireNonNull(connection.getRange(key.getBytes(StandardCharsets.UTF_8), start, end))));
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return "";
        }
    }


    @Override
    public Long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    @Override
    public boolean hmset(String key, Map<?, ?> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    @Override
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    @Override
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    @Override
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return null;
        }
    }

    @Override
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public Long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return null;
        }
    }

    @Override
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return null;
        }
    }

    @Override
    public boolean lSetAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPushIfPresent(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }

    }

    @Override
    public boolean lSetAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean rSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean rSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }

    }

    @Override
    public boolean rSetAll(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }

    }

    @Override
    public boolean rSetAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return false;
        }
    }

    @Override
    public Long lRemove(String key, long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
            return 0L;
        }
    }

    @Override
    public void rangeRemove(String key, Long stard, Long end) {
        try {
            redisTemplate.opsForList().trim(key, stard, end);
        } catch (Exception e) {
            LOGGER.error("method expire execute failed", e);
        }
    }

    @Override
    public void setBit(String key, long offset, boolean value) {
        redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public Boolean getBit(String key, long offset) {
        return redisTemplate.opsForValue().getBit(key, offset);
    }

    @Override
    public Long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Long hincr(String bucket, String key, long delta) {
        return redisTemplate.opsForHash().increment(bucket, key, delta);
    }

    @Override
    public Boolean hexists(String key, String value) {
        return redisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.hExists(key.getBytes(StandardCharsets.UTF_8), value.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return redisTemplate.opsForHash().entries(key)
                .entrySet().stream().collect(
                        Collectors.toMap(k -> (String) (k.getKey()), v -> (String) (v.getValue())));
    }

    @Override
    public void lPush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public void rPush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public String lPop(String key) {
        return String.valueOf(redisTemplate.opsForList().leftPop(key));
    }

    @Override
    public String rPop(String key) {
        return String.valueOf((redisTemplate.opsForList().rightPop(key)));
    }

    @Override
    public Long geoAdd(String key, Double longitude, Double latitude, String member) {
        return redisTemplate.opsForGeo().add(key, new Point(longitude, latitude), member);
    }

    @Override
    public Distance geoDist(String key, String member1, String member2) {
        return redisTemplate.opsForGeo().distance(key, member1, member2);
    }
}
