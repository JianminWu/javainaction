package com.ljljob.concurrent.juc.lock;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/17 10:17
 * @Function:
 * @Version 1.0
 */
public class ReentrantLockTest {

    private static final ReentrantLock lock = new ReentrantLock();

    private static int i = 0;

    public static void main(String[] args) {
        List<Thread> ts = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        if (i < 1000) {

                            try {
                                TimeUnit.MILLISECONDS.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            i++;
                            System.out.println(Thread.currentThread().getName() + "- " + i);
                        }
                    } finally {
                        lock.unlock();
                    }
                }
            }
            );
            ts.add(t);
        }
        ts.forEach(t -> {
            t.start();
        });
        System.out.println(i);
    }


}
