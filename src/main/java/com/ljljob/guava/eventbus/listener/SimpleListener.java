package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 10:03
 * @Function:
 * @Version 1.0
 */
@Data
@Slf4j
public class SimpleListener {
    @Subscribe
    public void doAction(String event) {
        log.info("[{}] action is doing ,event is [{}]", this.getClass().getSimpleName(), event);
    }

    @Subscribe
    public void doAction2(String event) {
        log.info("[{}] action is doing ,event is [{}] in action2", this.getClass().getSimpleName(), event);
    }

    @Subscribe
    public void doAction3(Integer event) {
        log.info("[{}] action is doing ,event is [{}] in action3", this.getClass().getSimpleName(), event);
    }
}
