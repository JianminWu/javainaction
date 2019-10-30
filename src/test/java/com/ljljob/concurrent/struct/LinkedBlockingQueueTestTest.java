package com.ljljob.concurrent.struct;

import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/10/25 17:23
 * @Function:
 * @Version 1.0
 */
public class LinkedBlockingQueueTestTest {

    private LinkedBlockingQueue queue = null;

    @Before
    public void setUp(){
        queue = LinkedBlockingQueueTest.create(2);
    }

    @Test
    public void testInsert(){
    }

    @After
    public void tearDown(){
        queue = null;
    }

}