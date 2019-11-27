package com.ljljob.guava.collections;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/11/5 15:11
 * @Function:
 * @Version 1.0
 */
public class ListsExampleTest {

    @Test
    public void testCreate(){
        ArrayList<Integer> l1 = Lists.newArrayList(1, 2, 3, 4, 5);
        assertThat(Joiner.on(",").join(l1),equalTo("1,2,3,4,5"));
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = Lists.newCopyOnWriteArrayList(l1);
        assertThat(copyOnWriteArrayList.size(),equalTo(5));
        ImmutableList<Character> strList = Lists.charactersOf("hello");
        assertThat(Joiner.on(",").join(strList),equalTo("h,e,l,l,o"));
    }

    @Test
    public void testCreateInitCapacity(){
        ArrayList<Integer> lists = Lists.newArrayListWithCapacity(2);
        lists.add(1);
        lists.add(2);
        ArrayList<Integer> lists2 = Lists.newArrayListWithExpectedSize(2);
        lists2.add(1);
        lists2.add(2);
        lists2.add(2);
        System.out.println(lists.size()+ " " + lists2.size());
    }

    /**
     * cartesian 笛卡尔积
     */
    @Test
    public void testCartesian(){
        ArrayList<Integer> l1 = Lists.newArrayList(1, 2, 3);
        ArrayList<Integer> l2 = Lists.newArrayList(4, 5);
        List<List<Integer>> cartesianProduct = Lists.cartesianProduct(l1, l2);
        cartesianProduct.stream().forEach(System.out::println);
    }

    @Test
    public void testPartition(){
        ArrayList<Integer> l1 = Lists.newArrayList(1, 2, 3);
        List<List<Integer>> partition = Lists.partition(l1, 2);
        partition.stream().forEach(System.out::println);
    }
}