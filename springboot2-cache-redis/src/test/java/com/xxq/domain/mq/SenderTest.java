package com.xxq.domain.mq;

import com.xxq.SpringbootDemoApplication;
import com.xxq.domain.mq.sender.FirstSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
 
/**
 * @author zhuzhe
 * @date 2018/5/25 16:00
 * @email 1529949535@qq.com
 */
@SpringBootTest(classes= SpringbootDemoApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SenderTest {
 
    @Autowired
    private FirstSender firstSender;

    @Test
    public void testSend(){
        String message = "test send";
        String uuid = UUID.randomUUID().toString();
        firstSender.send(uuid,message);
    }
}