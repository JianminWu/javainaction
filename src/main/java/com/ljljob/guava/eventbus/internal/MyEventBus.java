package com.ljljob.guava.eventbus.internal;

import java.util.concurrent.Executor;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:39
 * @Function:
 * @Version 1.0
 */
public class MyEventBus implements Bus {

    private final String busName;

    private static final String DEFAULT_BUS_NAME = "default";

    private static final String DEFAULT_TOPIC_NAME = "default-topic";

    /**
     * 封装EventBus中的注册信息(订阅者,订阅事件,是否取消)
     */
    private final MyRegistry registry = new MyRegistry();

    /**
     * 实际调用event的调度器
     */
    private final MyDispatcher dispatcher;

    public MyEventBus() {
        this(DEFAULT_BUS_NAME);
    }

    public MyEventBus(String busName) {
        this(busName, null, MyDispatcher.SEQ_EXECUTOR);
    }

    public MyEventBus(MyEventBusExceptionHandler exceptionHandler) {
        this(DEFAULT_BUS_NAME, exceptionHandler, MyDispatcher.SEQ_EXECUTOR);
    }
    public MyEventBus(String busName, MyEventBusExceptionHandler exceptionHandler) {
        this(busName, exceptionHandler, MyDispatcher.SEQ_EXECUTOR);
    }

    public MyEventBus(String busName, MyEventBusExceptionHandler exceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher = MyDispatcher.newDispatcher(executor, exceptionHandler);
    }

    @Override
    public void register(Object subscriber) {
        registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event, DEFAULT_TOPIC_NAME);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this,event, topic,registry);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
