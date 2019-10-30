package com.ljljob.concurrent.struct;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 17:03
 * @Function:
 * @Version 1.0
 * 无边界的BlockingQueue 要求加入的元素实现comparable接口 超过边界会扩容
 */
public class PriorityBlockingQueueTest {

    public static <E>PriorityBlockingQueue<E> create(int initialCapacity){
        return new PriorityBlockingQueue<>(initialCapacity);
    }
}
