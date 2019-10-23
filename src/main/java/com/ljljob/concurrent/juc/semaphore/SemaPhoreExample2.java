package com.ljljob.concurrent.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/16 17:26
 * @Function:
 * @Version 1.0
 */
public class SemaPhoreExample2 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        new Thread("T1 ") {
            @Override
            public void run() {
                try {
                    try {
                        semaphore.acquire(1);
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(String.format("finish %s job", Thread.currentThread().getName()));
                    } finally {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread("T2 ") {
            @Override
            public void run() {
                try {
                    try {
                        semaphore.acquire(2);
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(String.format("finish %s job", Thread.currentThread().getName()));
                    } finally {
                        semaphore.release(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread("T3 ") {
            @Override
            public void run() {
                try {
                    try {
                        semaphore.acquire(2);
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(String.format("finish %s job", Thread.currentThread().getName()));
                    } finally {
                        semaphore.release(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
