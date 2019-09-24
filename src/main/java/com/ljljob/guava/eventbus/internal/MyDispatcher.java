package com.ljljob.guava.eventbus.internal;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:46
 * @Function:
 * @Version 1.0
 */
public class MyDispatcher {

    private final MyEventBusExceptionHandler exceptionHandler;

    private final Executor executorService;

    static final Executor SEQ_EXECUTOR = SeqExecutor.SEQ_EXECUTOR;

    private MyDispatcher(Executor executorService, MyEventBusExceptionHandler exceptionHandler) {
        this.executorService = executorService;
        this.exceptionHandler = exceptionHandler;
    }

    public void dispatch(Bus eventBus, Object event, String topic, MyRegistry registry) {
        // 从注册表中获取一个topic下所有绑定的Subscriber实体
        ConcurrentLinkedQueue<MySubscriber> mySubscribers = registry.scanSubscriber(topic);
        if (null == mySubscribers) {
            if (exceptionHandler != null) {
                exceptionHandler.handler(new RuntimeException(" this topic [" + topic + "]has not bind yet!"),  new BaseEventBusContext(eventBus.getBusName(),null,event));
            }
            return;
        }
        // 判断是否是Disable状态,判断该方法的参数类型是不是event的父类
        // 调度执行方法
        mySubscribers.stream().filter(s -> !s.getIsDisable())
                .filter(s->{
                    Method subscribeMethod = s.getSubscribeMethod();
                    Class<?> aClass = subscribeMethod.getParameterTypes()[0];
                    return aClass.isAssignableFrom(event.getClass());
                })
                .forEach(s -> invokeSubscriber(s, event, eventBus));
    }

    /**
     *  实际调度执行 订阅方法
     * @param subscriber 订阅描述对象
     * @param event 事件参数
     * @param eventBus bus对象 用于出错封装context
     */
    private void invokeSubscriber(MySubscriber subscriber, Object event, Bus eventBus) {
        Object subscriberObject = subscriber.getSubscriberObject();
        Method subscribeMethod = subscriber.getSubscribeMethod();
        // 调度executor另起线程完成订阅方法
        executorService.execute(() -> {
            try {
                subscribeMethod.invoke(subscriberObject, event);
            } catch (Exception e) {
                if (null != exceptionHandler) {
                    exceptionHandler.handler(e, new BaseEventBusContext(eventBus.getBusName(),subscriber,event));
                }
            }
        });
    }

    /**
     * 封装错误信息的context对象
     */
    private  class BaseEventBusContext implements MyEventBusContext {
        private final String eventBusName;
        private final MySubscriber subscriber;
        private final Object event;

        public BaseEventBusContext(String eventBusName, MySubscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public String getBusName() {
            return this.eventBusName;
        }

        @Override
        public Object getSubscriber() {
            return this.subscriber.getSubscriberObject();
        }

        @Override
        public Object getEvent() {
            return this.event;
        }

        @Override
        public Method getSubscribeMethod() {
            return subscriber.getSubscribeMethod();
        }
    }

    public void close() {
        if (executorService instanceof ExecutorService)
            ((ExecutorService) executorService).shutdown();
    }

    static MyDispatcher newDispatcher(Executor executor, MyEventBusExceptionHandler handler) {
        return new MyDispatcher(executor, handler);
    }

    static MyDispatcher seqDispatcher(MyEventBusExceptionHandler handler) {
        return new MyDispatcher(SeqExecutor.SEQ_EXECUTOR, handler);
    }

    private static class SeqExecutor implements Executor {

        private static final SeqExecutor SEQ_EXECUTOR = new SeqExecutor();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

}
