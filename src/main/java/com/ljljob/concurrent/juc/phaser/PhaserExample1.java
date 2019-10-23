package com.ljljob.concurrent.juc.phaser;

import com.ljljob.classloader.chapter1.Parent;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/18 14:39
 * @Function:
 * @Version 1.0
 */
public class PhaserExample1 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        final Phaser phaser = new Phaser();

        IntStream.rangeClosed(0, 4).forEach(i -> {
            new TaskExample1(phaser).start();
        });
    }

    public static class TaskExample1 extends Thread {
        private final Phaser phaser;

        public TaskExample1(Phaser phaser) {
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " -> " + "start!");
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                System.out.println(Thread.currentThread().getName() + " -> " + "finished!");
                phaser.arriveAndAwaitAdvance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
