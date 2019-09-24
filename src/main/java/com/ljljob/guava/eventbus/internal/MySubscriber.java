package com.ljljob.guava.eventbus.internal;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 11:28
 * @Function:
 * @Version 1.0
 */
@Data
public class MySubscriber {

    Object subscriberObject;

    Method subscribeMethod;

    Boolean isDisable=Boolean.FALSE;

    public MySubscriber(Object subscriberObject, Method subscribeMethod) {
        this.subscriberObject = subscriberObject;
        this.subscribeMethod = subscribeMethod;
    }
}
