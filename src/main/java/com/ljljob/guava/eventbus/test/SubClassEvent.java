package com.ljljob.guava.eventbus.test;

import com.google.common.eventbus.EventBus;
import com.ljljob.guava.eventbus.listener.SubClassEventListener;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 16:56
 * @Function:
 * @Version 1.0
 */
public class SubClassEvent {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new SubClassEventListener());
        eventBus.post("test subclass ");
    }
}
