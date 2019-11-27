package com.ljljob.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/11/5 11:27
 * @Function:
 * @Version 1.0
 */
public class FluentIterableExampleTest {

    @Test
    public void testCreate(){

        FluentIterable<Integer> fit = FluentIterable.of(1, 2, 3, 4, 5);
        assertThat(fit.size(),equalTo(5));

        FluentIterable<Integer> fit2 = FluentIterable.from(new ArrayList<Integer>() {
            {
                add(1);
                add(2);
                add(3);
                add(4);
            }
        });
        assertThat(fit2.size(),equalTo(4));
    }

    /**
     * 类似stream map
     */
    @Test
    public void testTransform(){
        FluentIterable<Integer> fit = FluentIterable.of(1, 2, 3, 4, 5);
        fit.transform(i->i*10).forEach(System.out::println);
    }

    @Test
    public void testJoin(){
        FluentIterable<Integer> fit = FluentIterable.of(1, 2, 3, 4, 5);
        assertThat(fit.join(Joiner.on(",")),equalTo("1,2,3,4,5"));
    }

    @Test
    public void testCycle(){
        FluentIterable<Integer> fit = FluentIterable.of(1, 2, 3, 4, 5);
//        fit.cycle().forEach(System.out::println);
    }





}