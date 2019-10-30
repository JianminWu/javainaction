package com.ljljob.concurrent.struct;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: wujianmin
 * @Date: 2019/10/29 15:31
 * @Function:
 * @Version 1.0
 */
public class DelayQueueExampleTest {
    private DelayQueue queue;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);



    @Before
    public void setUp() throws Exception {
        queue = DelayQueueExample.create();
    }

    @Test
    public void testAdd() throws InterruptedException {
        assertThat(queue.add(new DelayQueueExample.MyDelayEle("1",2,TimeUnit.SECONDS)),equalTo(true));
        assertThat(queue.add(new DelayQueueExample.MyDelayEle("2",3,TimeUnit.SECONDS)),equalTo(true));
        assertThat(queue.add(new DelayQueueExample.MyDelayEle("3",4,TimeUnit.SECONDS)),equalTo(true));
        assertThat(queue.add(new DelayQueueExample.MyDelayEle("4",5,TimeUnit.SECONDS)),equalTo(true));
        assertThat(queue.size(),equalTo(4));
//        executorService.schedule(() -> {
//            try {
//                System.out.println(queue.take());
//                assertThat(queue.take(), equalTo("1"));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },4,TimeUnit.SECONDS);
        assertThat(queue.poll(), CoreMatchers.nullValue());
        TimeUnit.SECONDS.sleep(3);
        assertThat(queue.poll(),equalTo("1"));
    }

    @After
    public void tearDown() throws Exception {
        queue = null;
        executorService.awaitTermination(2,TimeUnit.HOURS);
        executorService.shutdown();
    }

}