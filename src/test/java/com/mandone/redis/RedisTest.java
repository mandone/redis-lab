package com.mandone.redis;

import com.mandone.redis.core.RedisService;
import com.mandone.redis.demo.advanced.bit.AccessLog;
import com.mandone.redis.demo.hash.BlogView;
import com.mandone.redis.demo.hash.UrlMapping;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UrlMapping urlMappingDemo;

    @Autowired
    private BlogView blogDemo;

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

    @Test
    public void setBit(){
        redisService.setBit("bit",1,true);
        redisService.setBit("bit",2,true);
        redisService.setBit("bit",4,true);
        redisService.setBit("bit",8,true);
    }

    @Test
    public void getBit(){
        Boolean bit = redisService.getBit("bit", 1);
        System.out.println(bit);
    }

    @Test
    public void bitCount(){
        Long bit = redisService.bitCount("bit");
        System.out.println(bit);
    }

    @Test
    public void urlTest(){
        String url = "http://redis.com/index.html";
        String shortUrl = urlMappingDemo.getShortUrl(url);
        System.out.println("页面上展示的短链接地址为：" + shortUrl);
        for(long i = 0L; i < 152; i++) {
            urlMappingDemo.incrementShortUrlAccessCount(shortUrl);
        }
        long accessCount = urlMappingDemo.getShortUrlAccessCount(shortUrl);
        System.out.println("短链接被访问的次数为：" + accessCount);
    }


    @Test
    public void blogTest(){
        // 发表一篇博客
        long id = blogDemo.getBlogId();
        Map<String, String> blog = new HashMap<String, String>();
        blog.put("id", String.valueOf(id));
        blog.put("title", "This is a TempBlog!!!");
        blog.put("content", "Nothing exists!!!");
        blog.put("author", "mandone");
        blog.put("time", new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
        blogDemo.publishBlog(id, blog);

        // 更新一篇博客
        Map<String, String> updatedBlog = new HashMap<String, String>();
        updatedBlog.put("title", "This is a TempBlog updated!!!");
        updatedBlog.put("content", "Something exists when updated it!!!");
        blog.put("time", new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
        blogDemo.updateBlog(id, updatedBlog);
        Map<String, String> blogResult = blogDemo.viewBlog(id);
        System.out.println("查看博客的详细内容：" + blogResult);
        blogDemo.incrementBlogLikeCount(id);
        blogResult = blogDemo.viewBlog(id);
        System.out.println("自己查看博客的详细内容：" + blogResult);
    }


    @Autowired
    private AccessLog accessLog;
    @Test
    public void accessLogTest(){
        String today = new DateTime().toString("yyyy-MM-dd");
        for (int number = 0; number < 10; number++) {
            String s = RandomStringUtils.randomNumeric(4, 5);
            Long uniqueId = Long.valueOf(s);
            accessLog.addRequest(uniqueId);
        }
        System.out.println(today + "日访问量:" + accessLog.getAccessCount());
    }
}
