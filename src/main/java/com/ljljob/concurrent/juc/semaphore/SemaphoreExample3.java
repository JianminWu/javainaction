package com.ljljob.concurrent.juc.semaphore;

import sun.security.ssl.SSLContextImpl;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/16 17:32
 * @Function:
 * @Version 1.0
 * <p>
 * acquireUninterruptibly() // 无视打断
 * acquireUninterruptibly(int) // 无视打断
 * drainPermits() // 获取所有Permits
 * hasQueuedThreads() // 获取是否有线程在wait队列
 */
public class SemaphoreExample3 {


    private static final Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                try {
                    semaphore.acquire(2);
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Thread has be interrupted");
                } finally {
                    semaphore.release();
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                try {
                    semaphore.acquireUninterruptibly(2);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName()+ " has be interrupted");
                } finally {
                    semaphore.release();
                }
            }
        };
        t2.start();
        new Thread() {
            @Override
            public void run() {
                t2.interrupt();
            }
        }.start();
    }

}
