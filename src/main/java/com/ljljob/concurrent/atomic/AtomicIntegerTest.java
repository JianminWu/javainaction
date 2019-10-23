package com.ljljob.concurrent.atomic;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/14 15:30
 * @Function:
 * @Version 1.0
 */
public class AtomicIntegerTest {

    private static AtomicInteger i = new AtomicInteger(0);


    public static void main(String[] args) {
//        demoInterger();
        usageForLambdaFinalCondition();

    }

    private static void usageForLambdaFinalCondition() {
        int k = 0;
        AtomicInteger atomicK = new AtomicInteger(k);
        List<Thread> ts = Lists.newArrayList();
        IntStream.rangeClosed(0, 10).forEach(i -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (atomicK.get() <= 10000) {
                        atomicK.getAndIncrement();
                    }
                }
            };
            ts.add(t);
        });
        ts.forEach(t -> {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(atomicK.get());
    }

    private static void demoInterger() {
        List<Thread> ts = Lists.newArrayList();
        IntStream.rangeClosed(0, 10).forEach(j -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (i.get() < 100000) {
                        i.getAndIncrement();
                    }
                }
            };
            ts.add(t);
        });
        ts.forEach(t -> {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(i.get());
    }
}
