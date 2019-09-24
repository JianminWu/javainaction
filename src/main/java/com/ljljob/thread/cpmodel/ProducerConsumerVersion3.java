package com.ljljob.thread.cpmodel;

import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/17 10:03
 * @Function:
 * @Version 1.0
 */
public class ProducerConsumerVersion3 {

    private Thread thread;

    private Integer i = 1;

    private final Object LOCK = new Object();

    private volatile Boolean isProduced = Boolean.FALSE;

    public void produce() {
        synchronized (LOCK) {
            while (isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("P->" + i++);
            isProduced = Boolean.TRUE;
            LOCK.notifyAll();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumer() {
        synchronized (LOCK) {
            while (!isProduced) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("C->" + i);
            isProduced = Boolean.FALSE;
            LOCK.notifyAll();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
