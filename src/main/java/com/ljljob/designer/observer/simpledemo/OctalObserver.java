package com.ljljob.designer.observer.simpledemo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:32
 * @Function:
 * @Version 1.0
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    void update() {
        int state = subject.getState();
        System.out.println(String.format("OctalObserver val change to %s", Integer.toOctalString(state)));
    }
}
