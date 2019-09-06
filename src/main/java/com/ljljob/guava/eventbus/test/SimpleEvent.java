package com.ljljob.guava.eventbus.test;

import com.google.common.eventbus.EventBus;
import com.ljljob.guava.eventbus.listener.SimpleListener;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 10:00
 * @Function:
 * @Version 1.0
 */
public class SimpleEvent {

    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new SimpleListener());
        eventBus.post("test event");
        eventBus.post(123);
    }
}
