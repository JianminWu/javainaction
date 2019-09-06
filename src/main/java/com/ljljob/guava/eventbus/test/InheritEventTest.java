package com.ljljob.guava.eventbus.test;

import com.google.common.eventbus.EventBus;
import com.ljljob.guava.eventbus.event.Apple;
import com.ljljob.guava.eventbus.event.Fruit;
import com.ljljob.guava.eventbus.listener.FruitListener;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:03
 * @Function:
 * @Version 1.0
 */
public class InheritEventTest {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new FruitListener());
        eventBus.post(new Fruit("fruit"));
        System.out.println("*********************");
        eventBus.post(new Apple("apple"));
    }
}
