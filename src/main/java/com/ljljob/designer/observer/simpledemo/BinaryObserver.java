package com.ljljob.designer.observer.simpledemo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:28
 * @Function:
 * @Version 1.0
 */
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    @Override
    void update() {
        int state = super.subject.getState();
        System.out.println(String.format("BinaryObserver val change to %s", Integer.toBinaryString(state)));
    }
}
