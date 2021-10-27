package com.chen.cache;

import com.chen.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.util.concurrent.locks.ReadWriteLock;

public class RedisCache implements Cache {
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }


    /*
    查询操作 调用此方法
    删除不会
     */
    @Override
    public void putObject(Object key, Object value) {
        RedisTemplate redisTemplate = getRedisTemplate();

        System.out.println("id:"+id);
        System.out.println("key:"+key);
        System.out.println("value:"+value);
        redisTemplate.opsForHash().put(id.toString(),getKeyMd5(key.toString()),value);
    }

    @Override
    public Object getObject(Object key) {
        RedisTemplate redisTemplate = getRedisTemplate();

        System.out.println("id"+id);
        System.out.println("key:"+key);
        return redisTemplate.opsForHash().get(id.toString(), getKeyMd5(key.toString()));
    }

    @Override
    public Object removeObject(Object o) {
        System.out.println("removeObject!!!");
        return null;
    }
    /*
    增删改操作 调用此方法
     */
    @Override
    public void clear() {
        System.out.println("clear!!!");
        RedisTemplate redisTemplate = getRedisTemplate();
        //清除id---mapperNamespace的缓存
        Boolean delete = redisTemplate.delete(id);
        System.out.println("清除redis缓存："+delete);
    }

    @Override
    public int getSize() {
        RedisTemplate redisTemplate = getRedisTemplate();
        return redisTemplate.opsForHash().size(id).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return null;
    }
    /*
    对RedisTemplate的获得封装
     */
    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
    //md5DigestAsHex返回 32位16进制的字符串 （32个字符）
    private String getKeyMd5(String key){
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
