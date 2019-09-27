package com.ljljob.designer.countDown;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:40
 * @Function:
 * @Version 1.0
 */
public class MyCountDownTest {

    public static void main(String[] args) {
        MyCountDown countDownLatch = new MyCountDown(5);

        IntStream.rangeClosed(1, 5).forEach(i ->
                new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }).start()
        );


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("wait for all multiple thread done");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("all work has done");
    }

}
