package com.ljljob.guava.eventbus.internal;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:42
 * @Function:
 * @Version 1.0
 */
public interface MyEventBusExceptionHandler {

    void handler(Throwable cause,MyEventBusContext context);
}
