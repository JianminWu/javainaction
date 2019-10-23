package com.ljljob.concurrent.juc.countdownlatch;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/15 11:08
 * @Function:
 * @Version 1.0
 */
public class CountDownLatchExample1 {

    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> ts = Lists.newArrayList();
        IntStream.rangeClosed(1,5).forEach(i->{
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(random.nextInt(5));
                        System.out.println(Thread.currentThread().getName() + " has finished.");
                        countDownLatch.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            ts.add(t);
        });
        ts.forEach(t->{t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All job has finished");
    }
}
