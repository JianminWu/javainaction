package com.ljljob.thread.cpmodel;

import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/17 10:03
 * @Function:
 * @Version 1.0
 */
public class ProducerConsumerVersion2 {

    private Thread thread;

    private Integer i = 1;

    private final Object LOCK = new Object();

    private volatile Boolean isProduced = Boolean.FALSE;

    public void produce() {
        synchronized (LOCK) {
            while (true) {
                if (isProduced) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("P->" + i++);
                    isProduced = Boolean.TRUE;
                    LOCK.notify();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void comsumer() {
        synchronized (LOCK) {
            while (true) {
                if (!isProduced) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("C->" + i);
                    isProduced = Boolean.FALSE;
                    LOCK.notify();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
