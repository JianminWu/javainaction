package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 16:55
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class SubClassEventListener extends BaseEventListener {

    @Subscribe
    public void subclassEventHandler(String event) {
        log.info("[{}] action is doing ,event is [{}]", this.getClass().getSimpleName(), event);
    }
}
