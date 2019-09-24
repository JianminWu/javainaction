package com.ljljob.guava.eventbus.internal;

import com.google.common.collect.Lists;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 11:27
 * @Function:
 * @Version 1.0
 */
public class MyRegistry {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<MySubscriber>> subscriberContainer = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        // 获取所有subscriber的@MySubscriber方法
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.stream().forEach(method -> tierSubscriber(subscriber, method));
    }

    private List<Method> getSubscribeMethods(Object subscriber) {
        List<Method> methods = Lists.newArrayList();
        Class<?> temp = subscriber.getClass();

        while (temp != null) {
            Method[] declaredMethods = temp.getDeclaredMethods();
            Arrays.stream(declaredMethods).filter(method -> method.isAnnotationPresent(MySubscribe.class)
                    && method.getParameterCount() == 1
                    && method.getModifiers() == Modifier.PUBLIC)
                    .forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }

    /**
     * topic跟订阅描述对象绑定到ConcurrentHashMap中
     * 数据结构类似
     * <pre>
     *     topic1 -> event1
     *            -> event2
     *            -> event3
     *     topic2 -> event1
     *            -> event2
     *            -> event3
     * </pre>
     * @param subscriber
     * @param method
     */
    private void tierSubscriber(Object subscriber, Method method) {
        String topic = method.getAnnotation(MySubscribe.class).topic();
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue());
        subscriberContainer.get(topic).add(new MySubscriber(subscriber, method));
    }

    ConcurrentLinkedQueue<MySubscriber> scanSubscriber(String topic){
        return subscriberContainer.get(topic);
    }


    /**
     * 简化操作解绑只是修改了标志位
     * @param subscriber
     */
    public void unbind(Object subscriber) {
        subscriberContainer.forEach((key,queue)->{
            queue.forEach(s->{
               if (s.getSubscriberObject() == subscriber)
                   s.setIsDisable(Boolean.TRUE);
            });
        });
    }

}
