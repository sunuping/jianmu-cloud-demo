package com.jianmu.config;


import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * @author lime
 * redis 缓存配置
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CommonsRedisCacheConfig {
    @Bean()
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //改用fast json 序列花
        FastJson2JsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJson2JsonRedisSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(fastJsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

}
