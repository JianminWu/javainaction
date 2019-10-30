package com.ljljob.concurrent.struct;


import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 16:21
 * @Function:
 * @Version 1.0
 *
 * take() 会Block
 * put() 满了会Block
 */
public class ArrayBlockQueueTestTest {

    private ArrayBlockingQueue queue = null;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    @Before
    public void setUp() {
        queue = ArrayBlockQueueTest.create(3);
    }

    @Test()
    public void testInsert() throws InterruptedException {
        assertThat(queue.add(1), equalTo(true));
        assertThat(queue.add(2), equalTo(true));
        assertThat(queue.add(3), equalTo(true));
//        assertThat(queue.add(4), equalTo(true));
        // ===================
        assertThat(queue.peek(), equalTo(1));
        assertThat(queue.peek(), equalTo(1));
        // ====================
        assertThat(queue.element(), equalTo(1));
        assertThat(queue.element(), equalTo(1));
        // =====================

        assertThat(queue.remainingCapacity(), equalTo(0));
        assertThat(queue.offer(3), equalTo(false));

        executor.schedule(() -> {
            try {
                assertThat(queue.take(), equalTo(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);
        queue.put(444);
//        fail("cant process here");
    }

    @Test
    public void testTake() throws InterruptedException {
        assertThat(queue.add(1), equalTo(true));
        assertThat(queue.add(2), equalTo(true));
        assertThat(queue.add(3), equalTo(true));
        // =================
        assertThat(queue.poll(),equalTo(1));
        assertThat(queue.poll(),equalTo(2));
        assertThat(queue.poll(),equalTo(3));
        // ==============

        assertThat(queue.remainingCapacity(),equalTo(3));

        // ==========

        executor.schedule(()->{
            queue.add(22);
        },1,TimeUnit.SECONDS);

        assertThat(queue.take(),equalTo(22));
    }

    @After
    public void tearDown() {
        queue = null;
    }
}
