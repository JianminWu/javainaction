package com.ljljob.designer.readwritelock;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 11:11
 * @Function:
 * @Version 1.0
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
//        SharedData sharedData = new SharedData(6);
//        IntStream.rangeClosed(0, 4).forEach(i -> {
//            new ReadWorker(sharedData).start();
//        });
//        IntStream.rangeClosed(0, 2).forEach(i -> {
//            new WriteWorker(sharedData, "writer" + i, "dsadasxzcda").start();
//        });
        SharedData data = new SharedData(10);
        new ReadWorker(data, "1").start();
        new ReadWorker(data, "2").start();
        new ReadWorker(data, "3").start();
        new ReadWorker(data, "4").start();
        new ReadWorker(data, "5").start();

        new WriteWorker(data, "1", "qwertyuiop").start();
        new WriteWorker(data, "2", "QWERTYUIOP").start();
    }

}
