package com.soho.configuration.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by ZhongChongtao on 2017/2/10.
 */
@Configuration
public class RedisCacheConfiguration {
    @Autowired
    @Bean
    @Primary
    @ConditionalOnClass(CacheManager.class)
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        // 设置缓存过期时间
        redisCacheManager.setDefaultExpiration(60 * 60 * 24);    // 秒
        // 使用前缀
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }

    @Autowired
    @Bean
    @ConditionalOnClass(CacheManager.class)
    public CacheManager thirdCacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        // 设置缓存过期时间
        redisCacheManager.setDefaultExpiration(60 * 60 * 24);    // 秒
        // 使用前缀
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }
}

