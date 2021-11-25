package com.mandone.redis;

import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class LettuceTest {


    @Test
    public void testCluster(){

        RedisClusterClient redisClient = RedisClusterClient.create("redis://192.168.111.144:7001");
        StatefulRedisClusterConnection<String, String> connection = redisClient.connect();
        System.out.println("Connected to Redis");
        RedisAdvancedClusterCommands<String, String> clusterCommands = connection.sync();
        clusterCommands.set("Hello","world");
        clusterCommands.set("php","nice");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name","mandone");
        clusterCommands.hset("myHash",hashMap);
        connection.close();
        redisClient.shutdown();
    }
}
