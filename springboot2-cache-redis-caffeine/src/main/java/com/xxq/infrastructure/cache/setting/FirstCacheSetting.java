package com.xxq.infrastructure.cache.setting;

import com.github.benmanes.caffeine.cache.CaffeineSpec;
import lombok.Getter;

public class FirstCacheSetting {
    @Getter
    private String cacheSpecification;
    /**
     * 一级缓存配置，配置项请点击这里 {@link CaffeineSpec#configure(String, String)}
     * @param cacheSpecification
     */
    public FirstCacheSetting(String cacheSpecification) {
        this.cacheSpecification = cacheSpecification;
    }
}
