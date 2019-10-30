package com.ljljob.concurrent.struct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: wujianmin
 * @Date: 2019/10/28 11:43
 * @Function:
 * @Version 1.0
 */
public class SynchronousQueueTestTest {
    SynchronousQueue<String> queue = null;

    @Before
    public void setUp() {

        queue = SynchronousQueueTest.create();
    }

    @Test
    public void testAdd() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(()->{
            try {
                String result = queue.take();
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
//        assertThat(queue.offer("123",1, TimeUnit.SECONDS),equalTo(true));
        TimeUnit.MILLISECONDS.sleep(20);
        assertThat(queue.offer("123"),equalTo(true));
    }


    @After
    public void tearDown() {
        queue = null;
    }
}