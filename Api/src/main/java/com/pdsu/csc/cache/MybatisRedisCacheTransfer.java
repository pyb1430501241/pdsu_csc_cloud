package com.pdsu.csc.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 半梦
 * @create 2021-02-20 17:41
 * mybatis redis 适配器
 */
public class MybatisRedisCacheTransfer {

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        MybatisRedisCache.setRedisTemplate(redisTemplate);
    }

}
