package com.ljljob.designer.observer.simpledemo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:22
 * @Function:
 * @Version 1.0
 */
public abstract class Observer {

    Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attachObserver(this);
    }

    abstract void update();


}
