package com.xxq.configure;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigure2 {
    // 队列名称
    public final static String SPRING_BOOT_QUEUE = "spring-boot-queue-2";
    // 交换机名称
    public final static String SPRING_BOOT_EXCHANGE = "spring-boot-exchange-2";
    // 绑定的值
    public static final String SPRING_BOOT_BIND_KEY = "spring-boot-bind-key-2";
}
