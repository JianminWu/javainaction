package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 10:51
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class DeadEventListener {
    @Subscribe
    public void handler(DeadEvent event){
        log.info("[{}] action is doing ,event is [{}]", this.getClass().getSimpleName(), event.getEvent());
        log.info("[{}] action is doing ,event is [{}]", this.getClass().getSimpleName(), event.getSource());
    }

}
