package com.xxq.domain.mq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者1
 */
@Component
public class FirstConsumer {
    @RabbitListener(queues = {"first-queue", "second-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) {
        // 处理消息
        System.out.println("FirstConsumer handleMessage :" + message);
    }
}