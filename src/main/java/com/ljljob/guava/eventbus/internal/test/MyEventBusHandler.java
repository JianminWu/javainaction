package com.ljljob.guava.eventbus.internal.test;

import com.ljljob.guava.eventbus.internal.MySubscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 15:01
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class MyEventBusHandler {

    @MySubscribe
    public void testEventBus1(String event) {
        log.info(event + "testEventBus1");
    }

    @MySubscribe
    public void testEventBus2(String event) {
        log.info(event + "testEventBus2");
    }

    @MySubscribe(topic = "topic1")
    public void testTopic(String event) {
        log.info(event + "testTopic");
    }

    @MySubscribe
    public void testException(Integer event) {
        throw new RuntimeException("test Exception");
    }


}
