package com.ljljob.concurrent.struct;

import cn.hutool.core.collection.ConcurrentHashSet;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentSkipListSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * @Author: wujianmin
 * @Date: 2019/10/30 15:51
 * @Function:
 * @Version 1.0
 */
public class ConcurrentSkipListSetExampleTest {
    private ConcurrentSkipListSet<Integer> set;

    @Before
    public void setUp() throws Exception {
        set = new ConcurrentSkipListSet();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);
        set.add(5);
        set.add(10);
        set.add(20);
    }

    /**
     * 找最近相邻
     * ceiling   向上找
     * floor     向下找
     */
    @Test
    public void testCeilingAndFloor(){
        assertThat(set.ceiling(9),equalTo(10));
        assertThat(set.ceiling(10),equalTo(10));
        assertThat(set.floor(19),equalTo(10));
        assertThat(set.floor(3000),equalTo(20));
    }

    @After
    public void tearDown() throws Exception {
        set = null;
    }

}