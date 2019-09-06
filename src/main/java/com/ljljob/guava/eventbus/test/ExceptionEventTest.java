package com.ljljob.guava.eventbus.test;

import com.google.common.eventbus.EventBus;
import com.ljljob.guava.eventbus.listener.ExceptionListener;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:22
 * @Function:
 * @Version 1.0
 */
public class ExceptionEventTest
{
    public static void main(String[] args) {
        EventBus eventBus = new EventBus((exception,context)->{
            System.out.println(exception.getMessage());
            System.out.println(context.getEvent());
            System.out.println(context.getEventBus());
            System.out.println(context.getSubscriber());
            System.out.println(context.getSubscriberMethod());
        });
        eventBus.register(new ExceptionListener());
        eventBus.post("test exception event bus");
    }
}
