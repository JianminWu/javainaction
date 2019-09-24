package com.ljljob.guava.eventbus.internal;

import java.lang.reflect.Method;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:43
 * @Function:
 * @Version 1.0
 */
public interface MyEventBusContext {

    String getBusName();

    Object getSubscriber();

    Object getEvent();

    Method getSubscribeMethod();

}
