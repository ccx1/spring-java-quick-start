package com.ccx.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Configuration
public class RedisConfig {


    @Autowired
    private RedisProperties mRedisProperties;


    /**
     * 使用redis的工具类。 走的是这个bean
     *
     * @param factory
     * @return
     */
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 基于SpringBoot2 对 RedisCacheManager 的自定义配置
     * 使用redis 注解形式
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        //设置CacheManager的值序列化方式为json序列化
        RedisSerializer<Object> jsonSerializer = new GenericJackson2JsonRedisSerializer();

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);
        // 获取默认时间
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);
        Duration defTime = mRedisProperties.getDefTime();
        if (defTime != null) {
            // 查看配置文件有没有默认时间
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(defTime);
        } else {
            // 如果没有配置时间， 默认1天。 1000 * 60 * 60 * 24;
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(Duration.ofDays(1));
        }
        // 缓存数据 key 设置
        Set<String> cacheNames = new HashSet<>();
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        Map<String, Duration> config = mRedisProperties.getConfig();
        if (config != null) {
            // 设置失效时间
            for (Map.Entry<String, Duration> entry : config.entrySet()) {
                cacheNames.add(entry.getKey());
                // 设置秒， 从底部源码查看， 发现是底层new一个config对象
                configMap.put(entry.getKey(), redisCacheConfiguration.entryTtl(entry.getValue()));
            }
        }
        //初始化RedisCacheManager
        return RedisCacheManager.builder(redisCacheWriter)
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
    }
}
