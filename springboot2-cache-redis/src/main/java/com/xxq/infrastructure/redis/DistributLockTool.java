package com.xxq.infrastructure.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
@Component
public class DistributLockTool {
    @Autowired
    private JedisPool jedisPool;//Redis客户端
    private static final String LOCK_SUCCESS = "OK";
    /**
     *  nx ： not exists, 只有key 不存在时才把key value set 到redis
     *  xx ： is exists ，只有 key 存在是，才把key value set 到redis
     */
    private static final String SET_IF_NOT_EXIST = "NX";
    /**
     *   ex ： seconds 秒
     *   px :  milliseconds 毫秒
     */
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    private static final Integer EXPIRE_TIME = 5000;//5s
    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        String result = jedisPool.getResource().set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        return LOCK_SUCCESS.equals(result);
    }

    public boolean tryGetDistributedLock(String lockKey, String requestId) {
        return tryGetDistributedLock(lockKey,requestId,EXPIRE_TIME);
    }
    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedisPool.getResource().eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        return RELEASE_SUCCESS.equals(result);
    }
}