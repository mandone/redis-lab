package com.mandone.redis.demo.hash;

import com.mandone.redis.core.RedisService;
import org.springframework.stereotype.Component;

@Component
public class UrlMapping {

    private final RedisService redisService;

    private static final String[] X36_ARRAY = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");

    public UrlMapping(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * 获取短连接网址
     * @param url 长链接地址
     * @return 短链接地址
     */
    public String getShortUrl(String url) {
        long shortUrlSeed = redisService.incr("short_url_seed",1);

        StringBuilder buffer = new StringBuilder();
        while(shortUrlSeed > 0) {
            buffer.append(X36_ARRAY[(int)(shortUrlSeed % 36)]);
            shortUrlSeed = shortUrlSeed / 36;
        }
        String shortUrl = buffer.reverse().toString();

        redisService.hset("short_url_access_count", shortUrl, "0");
        redisService.hset("url_mapping", shortUrl, url);

        return shortUrl;
    }

    /**
     * 给短连接地址进行访问次数的增长
     * @param shortUrl:短链接
     */
    public void incrementShortUrlAccessCount(String shortUrl) {
        redisService.hincr("short_url_access_count", shortUrl, 1);
    }

    /**
     * 获取短连接地址的访问次数
     * @param shortUrl:短链接
     */
    public Long getShortUrlAccessCount(String shortUrl) {
        return Long.parseLong(String.valueOf(redisService.hget("short_url_access_count", shortUrl)));
    }
}
