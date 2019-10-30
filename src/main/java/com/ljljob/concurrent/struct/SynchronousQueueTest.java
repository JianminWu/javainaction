package com.ljljob.concurrent.struct;

import java.util.concurrent.SynchronousQueue;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 17:27
 * @Function:
 * @Version 1.0
 * Feature
 * 1. A synchronous queue does not have any internal capacity
 * 2. you cannot insert an element (using any method) unless another thread is trying to remove it
 * 3. you cannot iterate as there is nothing to iterate
 */
public class SynchronousQueueTest {

    public static <E>SynchronousQueue<E> create(){
        return new SynchronousQueue<>();
    }
}
