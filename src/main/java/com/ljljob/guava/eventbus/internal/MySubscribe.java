package com.ljljob.guava.eventbus.internal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: wujianmin
 * @Date: 2019/9/10 10:55
 * @Function:
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MySubscribe {
    String topic() default "default-topic";
}
