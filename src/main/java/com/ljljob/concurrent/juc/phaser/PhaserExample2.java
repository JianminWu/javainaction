package com.ljljob.concurrent.juc.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/18 14:46
 * @Function:
 * @Version 1.0
 */
public class PhaserExample2 {
    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        // 指定数量的phaser 需要等到所有promit 消费为0之后线程才释放,否则阻塞
        // 3个线程 3个阶段 调用3次getArrivedParties()
        final Phaser phaser = new Phaser(3) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("*** " + phase + " *** " + "+++ " + registeredParties + " +++");
                switch (phase) {
                    case 0:
                        return finishFootball();
                    case 1:
                        return finishRunning();
                    case 2:
                        return finishBicycle();
                    default:
                        return true;
                }
//                return false;
            }

            private boolean finishBicycle() {
                System.out.println("finish bicycle");
                return false;
            }

            private boolean finishRunning() {
                System.out.println("finish running");
                return false;
            }

            private boolean finishFootball() {
                System.out.println("finish football");
                return false;
            }
        };
        IntStream.rangeClosed(1, 3).forEach(i -> {
            new TaskExample1(phaser).start();
        });
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(phaser.getArrivedParties());
    }

    public static class TaskExample1 extends Thread {
        private final Phaser phaser;

        public TaskExample1(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " -> " + "start!");
            try {

                sport("football");
                phaser.arriveAndAwaitAdvance();
                TimeUnit.SECONDS.sleep(random.nextInt(2));
                sport("running");
                phaser.arriveAndAwaitAdvance();
                TimeUnit.SECONDS.sleep(random.nextInt(2));
                sport("bicycle");
                phaser.arriveAndAwaitAdvance();
                TimeUnit.SECONDS.sleep(random.nextInt(2));
                System.out.println(Thread.currentThread().getName() + " -> " + "finished!");
                System.out.println(phaser.getPhase());
                System.out.println(phaser.getRegisteredParties());
                System.out.println("====================");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void sport(String sportName) {
            System.out.println(Thread.currentThread().getName() + " doing " + sportName);
        }
    }
}
