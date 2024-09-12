package com.marre.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate redisTemplate = new RedisTemplate();

        //设置连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置redis Key 序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        log.info("Redis模板对象创建完成");
        return redisTemplate;
    }
}
