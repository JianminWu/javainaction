package com.ljljob.guava.eventbus.internal.test;

import com.ljljob.guava.eventbus.internal.MyEventBus;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 15:00
 * @Function:
 * @Version 1.0
 */
public class MyEventBusTest {

    public static void main(String[] args) {
        MyEventBus eventBus = new MyEventBus((cause,context)->{
            System.out.println(cause);
            System.out.println(context.getBusName());
            System.out.println(context.getSubscribeMethod());
            System.out.println(context.getSubscriber());
            System.out.println(context.getEvent());
        });
        eventBus.register(new MyEventBusHandler());
        eventBus.post("test111");
        eventBus.post(123);
        eventBus.post("testTopic1","topic1");
    }
}
