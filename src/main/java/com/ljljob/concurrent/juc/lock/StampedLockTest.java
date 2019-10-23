package com.ljljob.concurrent.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/17 14:50
 * @Function:
 * @Version 1.0
 */
public class StampedLockTest {

    private final static StampedLock lock = new StampedLock();

    private static volatile boolean isDone = false;

    private static int i = 0;

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        produce();
                    }
                }
            }.start();
        });
        IntStream.rangeClosed(1, 3).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        consume();
                    }
                }
            }.start();
        });
    }


    public static void produce() {
        long stamped = 1L;
        try {
            stamped = lock.writeLock();
            System.out.println("produce " + Thread.currentThread().getName() + " ->" + ++i);
        } finally {
            lock.unlockWrite(stamped);
        }
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void consumeOptimistic() {
        long stamped = 1L;
        int countI = i;
        stamped = lock.tryOptimisticRead();
        if (!lock.validate(stamped)) {
            stamped = lock.readLock();
            countI = i;
            lock.unlockRead(stamped);
        }

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("consumeOptimistic " + Thread.currentThread().getName() + " ->" + i);

    }

    public static void consume() {
        long stamped = 1L;
        try {
            stamped = lock.readLock();
            System.out.println("consume " + Thread.currentThread().getName() + " ->" + i);
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlockRead(stamped);
        }

    }

}
