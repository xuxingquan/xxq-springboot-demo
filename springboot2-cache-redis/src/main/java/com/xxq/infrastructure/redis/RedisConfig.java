package com.xxq.infrastructure.redis;
 
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
 
import java.lang.reflect.Method;
import java.time.Duration;
 
@Configuration
@EnableCaching
@EnableConfigurationProperties({JedisConfig.class})
public class RedisConfig extends CachingConfigurerSupport {
    private JedisConfig redisSetting;
    public RedisConfig(JedisConfig redisSetting){
        this.redisSetting = redisSetting;
    }
    /**
     * 自定义生成redis-key
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append(".");
            sb.append(method.getName()).append(".");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            System.out.println("keyGenerator=" + sb.toString());
            return sb.toString();
        };
    }

//    @Bean
//    public JedisPoolConfig jedisPoolConfig(){
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(redisSetting.getMaxIdle());
//        jedisPoolConfig.setMinIdle(redisSetting.getMinIdle());
//        jedisPoolConfig.setMaxTotal(redisSetting.getMaxActive());
//        jedisPoolConfig.setMaxWaitMillis(redisSetting.getMaxWaitMillis());
//        return jedisPoolConfig;
//    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisSetting.getHost());
        redisStandaloneConfiguration.setDatabase(0);//Redis数据库索引（默认为0）
        redisStandaloneConfiguration.setPort(redisSetting.getPort());
        JedisClientConfiguration.JedisClientConfigurationBuilder clientConfiguration = JedisClientConfiguration.builder();
        clientConfiguration.connectTimeout(Duration.ofMillis(redisSetting.getTimeout()));
        return  new JedisConnectionFactory(redisStandaloneConfiguration, clientConfiguration.build());
    }
 
    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(jedisConnectionFactory()))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    @Bean(value = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        // 使用String格式序列化缓存键 shiro时 valid报错
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}