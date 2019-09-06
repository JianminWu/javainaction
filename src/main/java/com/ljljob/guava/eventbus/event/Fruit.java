package com.ljljob.guava.eventbus.event;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: wujianmin
 * @Date: 2019/9/6 17:00
 * @Function:
 * @Version 1.0
 */
@Data
public class Fruit {
    public Fruit(String name) {
        this.name = name;
    }

    private String name;

}
