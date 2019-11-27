package com.ljljob.guava.collections;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/11/5 15:23
 * @Function:
 * @Version 1.0
 */
public class SetsExampleTest {

    @Test
    public void testCreate(){
        HashSet<Integer> s1 = Sets.newHashSet(1, 2, 3);
    }

}