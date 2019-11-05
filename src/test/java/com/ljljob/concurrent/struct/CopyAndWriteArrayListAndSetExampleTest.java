package com.ljljob.concurrent.struct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/30 16:02
 * @Function:
 * @Version 1.0
 *
 * 原理
 * 1. 在读的时候不加锁
 * 2. 在写的时候使用{@link java.util.concurrent.locks.ReentrantLock} 锁
 * 3. 每次写的时候创建一个新的副本,然后重新像引用指向新的副本
 * 4. 适用在读远远大于写的情况
 * 5. 在复制的时候有大量的性能开销
 */
public class CopyAndWriteArrayListAndSetExampleTest {

    private CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList();
    private CopyOnWriteArraySet<Integer> set = new CopyOnWriteArraySet();

    @Before
    public void setUp() throws Exception {
        IntStream.rangeClosed(1, 10).boxed().forEach(i -> {
            list.add(i);
            set.add(i);
        });
    }

    @Test
    public void test(){
//        list.
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        set = null;

    }

}