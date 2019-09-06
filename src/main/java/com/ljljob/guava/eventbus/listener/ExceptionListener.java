package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:23
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class ExceptionListener {

    @Subscribe
    public void testNormalAction(String event){
        log.info("[{}] testNormalAction action is doing ,event is [{}]", this.getClass().getSimpleName(), event);

    }
    @Subscribe
    public void testAbNormalAction(String event){
        log.info("[{}] testAbNormalAction action is doing ,event is [{}]", this.getClass().getSimpleName(), event);
        throw new RuntimeException("Custom Exception");

    }
}
