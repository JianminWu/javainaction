package com.ljljob.concurrent.struct;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 17:04
 * @Function:
 * @Version 1.0
 * The capacity, if unspecified,
 * is equal to {@link Integer#MAX_VALUE}.
 */
public class LinkedBlockingQueueTest {

    public static <E>LinkedBlockingQueue<E> create(int capacity){
        return new LinkedBlockingQueue(capacity);
    }
}
