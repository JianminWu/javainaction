package com.ljljob.concurrent.struct;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Author: wujianmin
 * @Date: 2019/10/29 15:28
 * @Function:
 * @Version 1.0
 *
 * optionally-bounded
 * 有序双向链表
 */
public class LinkedBlockingDequeExample {

    public static <E>LinkedBlockingDeque<E> create(){
       return new LinkedBlockingDeque<E>();
    }
}
