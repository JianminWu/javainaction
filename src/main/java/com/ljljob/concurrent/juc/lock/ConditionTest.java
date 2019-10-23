package com.ljljob.concurrent.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/17 14:52
 * @Function:
 * @Version 1.0
 */
public class ConditionTest {

    private static final ReentrantLock lock = new ReentrantLock();
    private static Condition readCondition = lock.newCondition();
    private static Condition writeCondition = lock.newCondition();
    private static volatile boolean isDone = false;
    private static int i = 0;

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    while (true){
                        produce();
                    }
                }
            }.start();
        });
        IntStream.rangeClosed(1, 3).forEach(i -> {
            new Thread() {
                @Override
                public void run() {
                    while (true){
                        consume();
                    }
                }
            }.start();
        });
    }

    public static void produce() {
        try {
            lock.lock();
            while (isDone) {
                writeCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + " producer -> " + i++);
            TimeUnit.MILLISECONDS.sleep(1);
            isDone = true;
            readCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public static void consume() {
        try {
            lock.lock();
            while (!isDone) {
                readCondition.await();
            }
            System.out.println(Thread.currentThread().getName() + " consumer -> " + i);
            TimeUnit.MILLISECONDS.sleep(1);
            isDone = false;
            writeCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }
}
