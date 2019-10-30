package com.ljljob.concurrent.struct;


import java.util.concurrent.ArrayBlockingQueue;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 16:17
 * @Function:
 * @Version 1.0
 * 有边界的BlockQueue
 */
public class ArrayBlockQueueTest {

    public static <E> ArrayBlockingQueue<E> create(int capacity) {
        return new ArrayBlockingQueue<E>(capacity);
    }
}
