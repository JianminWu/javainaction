package com.ljljob.concurrent.juc.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/18 16:22
 * @Function:
 * @Version 1.0
 *  Arrive方法 直接到达一个phaser 不等待其他phaser 完成直接往下执行
 */
public class PhaserExample3 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(2);
        Thread t1 = new Thread("T1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "--> running step1");
                int arrive = phaser.arrive();
                System.out.println(Thread.currentThread().getName() + "--> running step2");
                phaser.awaitAdvance(arrive);
                phaser.arriveAndAwaitAdvance();
            }
        };

        Thread t2 = new Thread("T2") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "--> running step1");
                phaser.arriveAndAwaitAdvance();
                System.out.println(Thread.currentThread().getName() + "--> running step2");
                phaser.arriveAndAwaitAdvance();
            }
        };
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }


}
