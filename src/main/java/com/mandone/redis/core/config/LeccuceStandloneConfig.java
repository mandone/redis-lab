package com.mandone.redis.core.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "spring.lettuce")
@Component
public class LeccuceStandloneConfig {

    @Value(value = "${spring.lettuce.host}")
    private String host;

    @Value(value = "${spring.lettuce.port}")
    private int port;

    @Bean("lettuceSingleFactory")
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration lettuceSingleConfig = new RedisStandaloneConfiguration(host,port);
        return new LettuceConnectionFactory(lettuceSingleConfig);
    }


    @Bean(name = "lettuceSingleTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("lettuceSingleFactory")LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //设置序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
