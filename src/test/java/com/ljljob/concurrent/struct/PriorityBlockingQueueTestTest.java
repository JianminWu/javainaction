package com.ljljob.concurrent.struct;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.PriorityBlockingQueue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 17:10
 * @Function:
 * @Version 1.0
 */
public class PriorityBlockingQueueTestTest {
    private  PriorityBlockingQueue queue=null;

    @Before
    public void setUp(){
        queue = PriorityBlockingQueueTest.create(3);
    }

    @Test
    public void testInsert(){
        assertThat(queue.add(1),equalTo(true));
        assertThat(queue.add(1),equalTo(true));
        assertThat(queue.add(1),equalTo(true));
        assertThat(queue.add(1),equalTo(true));
        assertThat(queue.add(1),equalTo(true));
        assertThat(queue.add(1),equalTo(true));
        // ===================
    }


    @After
    public void tearDown(){
        queue = null;
    }

}