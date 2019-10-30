package com.ljljob.concurrent.struct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingDeque;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * @Author: wujianmin
 * @Date: 2019/10/29 17:03
 * @Function:
 * @Version 1.0
 */
public class LinkedBlockingDequeExampleTest {
    private LinkedBlockingDeque queue;

    @Before
    public void setUp() throws Exception {
        queue = LinkedBlockingDequeExample.create();
    }

    @Test
    public void testAdd() throws Exception {
        assertThat(queue.offerFirst(1),equalTo(true));
        assertThat(queue.offerFirst(2),equalTo(true));
        assertThat(queue.offerFirst(3),equalTo(true));
        assertThat(queue.offerLast(4),equalTo(true));
        assertThat(queue.remove(),equalTo(3));
        assertThat(queue.remove(),equalTo(2));
        assertThat(queue.remove(),equalTo(1));
        assertThat(queue.remove(),equalTo(4));
    }

    @After
    public void tearDown() throws Exception {
        queue = null;
    }

}