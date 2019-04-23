package com.xxq.configure.aop;

import com.xxq.common.annotation.DistributLock;
import com.xxq.common.util.RandomUtil;
import com.xxq.infrastructure.redis.DistributLockTool;
import com.xxq.domain.service.DistributLockKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * redis分布式锁
 */
@Aspect
@Configuration
public class LockMethodInterceptor {
    @Autowired
    private DistributLockKeyGenerator cacheKeyGenerator;
    @Autowired
    private DistributLockTool redisTool;
    @Around("execution(public * *(..)) && @annotation(com.xxq.common.annotation.DistributLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DistributLock lock = method.getAnnotation(DistributLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        String requestId = RandomUtil.random();
        final String lockKey = cacheKeyGenerator.getLockKey(pjp);
        try {
            boolean success = redisTool.tryGetDistributedLock(lockKey, requestId);
            Assert.isTrue(success,"Do not repeat request");
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException("system error");
            }
        } finally {
            redisTool.releaseDistributedLock(lockKey,requestId);
        }
    }
}