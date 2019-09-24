package com.ljljob.designer.observer.simpledemo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:31
 * @Function:
 * @Version 1.0
 */
public class ObserverClientTest {

    public static void main(String[] args) {
        Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);

        subject.changeState(10);
        System.out.println("=====================");
        subject.changeState(15);

    }
}
