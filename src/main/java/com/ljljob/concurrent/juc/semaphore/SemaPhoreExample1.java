package com.ljljob.concurrent.juc.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/16 17:06
 * @Function: semaPhore Lock
 * @Version 1.0
 */
public class SemaPhoreExample1 {

    private static int i = 0;
    static final SemaphoreLock lock = new SemaphoreLock();

    public static void main(String[] args) {
        IntStream.rangeClosed(0, 2).forEach(j -> {
            new Thread() {
                @Override
                public void run() {
                    try {
                        lock.lock();
                        while (i < 10) {
                            i++;
                            System.out.println(i);
                            TimeUnit.SECONDS.sleep(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }.start();
        });
    }


    static class SemaphoreLock {
        private final Semaphore semaphore = new Semaphore(1);

        private void lock() throws InterruptedException {
            semaphore.acquire();
        }

        private void unlock() {
            semaphore.release();
        }
    }
}
