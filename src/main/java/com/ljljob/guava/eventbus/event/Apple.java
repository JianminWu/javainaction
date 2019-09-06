package com.ljljob.guava.eventbus.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:00
 * @Function:
 * @Version 1.0
 */
@Setter
@Getter
public class Apple extends Fruit {

    public Apple(String name) {
        super(name);
    }
}
