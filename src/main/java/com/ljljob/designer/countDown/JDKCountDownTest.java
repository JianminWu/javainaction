package com.ljljob.designer.countDown;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:14
 * @Function:
 * @Version 1.0
 */

/**
 * 使用countDown 可以实现等待某一些线程调度执行完之后在继续执行主线程逻辑
 */
public class JDKCountDownTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);

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
