package com.ljljob.designer.observer.simpledemo;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:21
 * @Function:
 * @Version 1.0
 */
public class Subject {

    private int state = 0;

    final List<Observer> observers = new ArrayList<>();

    void attachObserver(Observer observer) {
        this.observers.add(observer);
    }

    int getState() {
        return state;
    }

    public void changeState(int state) {
        if (this.state != state) {
            this.state = state;
            this.notifyAllObserver();
        }
    }

    private void notifyAllObserver() {
        if (CollectionUtil.isNotEmpty(observers)) {
            observers.stream().forEach(Observer::update);
        }
    }

}
