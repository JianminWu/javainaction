package com.ljljob.guava.eventbus.listener;

import com.google.common.eventbus.Subscribe;
import com.ljljob.guava.eventbus.event.Apple;
import com.ljljob.guava.eventbus.event.Fruit;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:04
 * @Function:
 * @Version 1.0
 */
@Slf4j
public class FruitListener {

    @Subscribe
    public void testFruitEat(Fruit fruit) {
        log.info("[{}] fruit action is doing ,event is [{}]", this.getClass().getSimpleName(), fruit);
    }

    @Subscribe
    public void testAppleEat(Apple apple) {
        log.info("[{}] apple action is doing ,event is [{}]", this.getClass().getSimpleName(), apple);
    }
}
