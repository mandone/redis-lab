package com.mandone.redis;

import com.mandone.redis.core.RedisService;
import com.mandone.redis.demo.advanced.bf.BloomFilterUtil;
import com.mandone.redis.demo.advanced.bit.AccessLog;
import com.mandone.redis.demo.advanced.expire.DistributeLock;
import com.mandone.redis.demo.advanced.expire.RedissonLock;
import com.mandone.redis.demo.advanced.geohash.DistanceCalculate;
import com.mandone.redis.demo.hash.BlogView;
import com.mandone.redis.demo.hash.UrlMapping;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {
    @Resource(name = "redisServiceImpl")
    private RedisService redisService;
    @Autowired
    private UrlMapping urlMappingDemo;

    @Autowired
    private BlogView blogDemo;

    @Test
    public void get() {
        Object aaa = redisService.set("hello","mandone");
        System.out.println(aaa);
    }

    @Test
    public void set() {
        Object aaa = redisService.set("Hello", "Mandone");
        System.out.println(aaa);
    }

    @Test
    public void setBit() {
        redisService.setBit("bit", 1, true);
        redisService.setBit("bit", 2, true);
        redisService.setBit("bit", 4, true);
        redisService.setBit("bit", 8, true);
    }

    @Test
    public void getBit() {
        Boolean bit = redisService.getBit("bit", 1);
        System.out.println(bit);
    }

    @Test
    public void bitCount() {
        Long bit = redisService.bitCount("bit");
        System.out.println(bit);
    }

    @Test
    public void urlTest() {
        String url = "http://redis.com/index.html";
        String shortUrl = urlMappingDemo.getShortUrl(url);
        System.out.println("???????????????????????????????????????" + shortUrl);
        for (long i = 0L; i < 152; i++) {
            urlMappingDemo.incrementShortUrlAccessCount(shortUrl);
        }
        long accessCount = urlMappingDemo.getShortUrlAccessCount(shortUrl);
        System.out.println("?????????????????????????????????" + accessCount);
    }


    @Test
    public void blogTest() {
        // ??????????????????
        long id = blogDemo.getBlogId();
        Map<String, String> blog = new HashMap<String, String>();
        blog.put("id", String.valueOf(id));
        blog.put("title", "This is a TempBlog!!!");
        blog.put("content", "Nothing exists!!!");
        blog.put("author", "mandone");
        blog.put("time", new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
        blogDemo.publishBlog(id, blog);

        // ??????????????????
        Map<String, String> updatedBlog = new HashMap<String, String>();
        updatedBlog.put("title", "This is a TempBlog updated!!!");
        updatedBlog.put("content", "Something exists when updated it!!!");
        blog.put("time", new DateTime().toString("yyyy-MM-dd hh:mm:ss"));
        blogDemo.updateBlog(id, updatedBlog);
        Map<String, String> blogResult = blogDemo.viewBlog(id);
        System.out.println("??????????????????????????????" + blogResult);
        blogDemo.incrementBlogLikeCount(id);
        blogResult = blogDemo.viewBlog(id);
        System.out.println("????????????????????????????????????" + blogResult);
    }


    @Autowired
    private AccessLog accessLog;

    @Test
    public void accessLogTest() {
        String today = new DateTime().toString("yyyy-MM-dd");
        for (int number = 0; number < 10; number++) {
            String s = RandomStringUtils.randomNumeric(4, 5);
            Long uniqueId = Long.valueOf(s);
            accessLog.addRequest(uniqueId);
        }
        System.out.println(today + "????????????:" + accessLog.getAccessCount());
    }

    @Autowired
    private DistanceCalculate distanceCalculate;


    @Test
    public void geoTest() {
        distanceCalculate.savePosition("?????????", 116.396822, 39.908789);
        distanceCalculate.savePosition("????????????", 114.109981, 22.542288);
        System.out.println(distanceCalculate.getDistance("?????????", "????????????"));
    }


    @Autowired
    private DistributeLock distributeLock;

    @Test
    public void testRedLock1() {
        System.out.println(distributeLock.lock("lock1", "lock_value", 100));
    }

    @Autowired
    private RedissonLock redissonLock;

    @Test
    public void testTryLock1() {
        redissonLock.tryLock("lock9");
    }

    @Test
    public void testLock1() {
        redissonLock.lock("lock999");
    }

    @Autowired
    private BloomFilterUtil bloomFilter;

    @Test
    public void testBloomFilter(){
        long num = 500;
        for(int i = 0;i < num;i ++){
            bloomFilter.add("bloom_box33",i);
        }
        long bloomCount = bloomFilter.count("bloom_box33");
        System.out.println("???????????????????????????:" + num);
        System.out.println("????????????????????????????????????:" + bloomCount);
        System.out.println("?????????:" + new BigDecimal(Math.abs(num - bloomCount))
                .divide(new BigDecimal(num),4,BigDecimal.ROUND_HALF_UP));
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTest.class);

    @Test
    public void testSentinel()  {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                //?????????"redis://"?????????SSL??????
                .addSentinelAddress("redis://192.168.111.137:26379")
                .addSentinelAddress("redis://192.168.111.137:26380")
                .addSentinelAddress("redis://192.168.111.137:26381")
                .setMasterConnectionMinimumIdleSize(10);
        RedissonClient redisson = Redisson.create(config);


        while (true){
            try {
                String key = RandomStringUtils.randomNumeric(5);
                long keyLon = Long.parseLong(key);
                redisson.getBitSet("bitset").set(keyLon,true);
                TimeUnit.SECONDS.sleep(1);
                System.out.println("key " + key + ",value " + true );
            }catch (Exception e){
                LOGGER.error("set failed",e);
            }
        }

    }

    @Test
    public void testCluster() throws InterruptedException {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000) // ????????????????????????????????????????????????
                //?????????"rediss://"?????????SSL??????
                .addNodeAddress("redis://192.168.111.144:7000", "redis://192.168.111.144:7003")
                .addNodeAddress("redis://192.168.111.144:7001", "redis://192.168.111.144:7004")
                .addNodeAddress("redis://192.168.111.144:7002", "redis://192.168.111.144:7005");
        RedissonClient redisson = Redisson.create(config);

        String key = RandomStringUtils.randomNumeric(5);
        long keyLon = Long.parseLong(key);
        redisson.getBitSet("bitset").set(keyLon,true);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("key " + key + ",value " + true );

    }

}
