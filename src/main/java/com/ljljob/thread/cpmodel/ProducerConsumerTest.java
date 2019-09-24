package com.ljljob.thread.cpmodel;

import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/18 10:41
 * @Function:
 * @Version 1.0
 */
public class ProducerConsumerTest {
    public static void main(String[] args) {
//        singleThreadTest();
        multipleThreadTest();
    }

    private static void singleThreadTest() {
        ProducerConsumerVersion2 pc2 = new ProducerConsumerVersion2();
        new Thread(() -> {
            pc2.produce();
        }).start();
        new Thread(() -> {
            pc2.comsumer();
        }).start();
    }

    private static void multipleThreadTest() {
        ProducerConsumerVersion3 pc3 = new ProducerConsumerVersion3();
        Stream.of("p1", "p2", "p3").forEach(name -> {
            new Thread() {
                @Override
                public void run() {
                    while (true)
                        pc3.produce();
                }
            }.start();
        });
        Stream.of("c1", "c2", "c3").forEach(name -> {
            new Thread() {
                @Override
                public void run() {
                    while (true)
                        pc3.consumer();
                }
            }.start();
        });
    }
}
