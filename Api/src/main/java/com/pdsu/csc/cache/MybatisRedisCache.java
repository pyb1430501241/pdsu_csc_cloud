package com.pdsu.csc.cache;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author 半梦
 * @create 2021-02-20 17:32
 */
@Log4j2
public class MybatisRedisCache implements Cache {

    private static RedisTemplate<String, Object> redisTemplate;

    private final String id;

    /**
     * The {@code ReadWriteLock}.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    public static void setRedisTemplate(@NonNull RedisTemplate<String, Object> redisTemplate) {
        MybatisRedisCache.redisTemplate = redisTemplate;
    }

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        try{
            if(null != value)
                redisTemplate.opsForValue().set(key.toString(), value,60, TimeUnit.SECONDS);//控制存放时间60s
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis保存数据异常！");
        }
    }

    @Override
    public Object getObject(Object key) {
        try{
            if(null != key)
                return redisTemplate.opsForValue().get(key.toString());
        }catch (Exception e){
            log.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        try{
            if(null != key)
                return redisTemplate.expire(key.toString(),1,TimeUnit.DAYS);//设置过期时间
        }catch (Exception e){
            e.printStackTrace();
            log.error("redis获取数据异常！");
        }
        return null;
    }

    @Override
    public void clear() {
        redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
            Long size = redisConnection.dbSize();
            //连接清除数据
            redisConnection.flushDb();
            redisConnection.flushAll();
            return size;
        });
    }

    @Override
    public int getSize() {
        Long size = redisTemplate.execute(RedisServerCommands :: dbSize);
        return size.intValue();
    }

}
