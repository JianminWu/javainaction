package com.ljljob.guava.eventbus.internal;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:38
 * @Function:
 * @Version 1.0
 */
public interface Bus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event,String topic);

    void close();

    String getBusName();
}
