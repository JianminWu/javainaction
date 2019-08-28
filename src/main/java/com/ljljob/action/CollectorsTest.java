package com.ljljob.action;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: wujianmin
 * @Date: 2019/8/27 10:30
 * @Function:
 * @Version 1.0
 */
public class CollectorsTest {
    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        testAverage();
        testCollectingAndThen();
    }

    public static void testAverage() {
        Optional.ofNullable(menu.stream().collect(Collectors.averagingInt(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingLong(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.averagingDouble(Dish::getCalories))).ifPresent(System.out::println);
    }


    public static int printDish(List<Dish> dishs) {
        dishs.stream().forEach(System.out::println);
        return dishs.size();
    }

    public static void testCollectingAndThen() {
        Integer ret = menu.stream().collect(Collectors.collectingAndThen(Collectors.toList(), CollectorsTest::printDish));
        System.out.println(ret);
    }

    public static void testCounting(){
        Optional.ofNullable(menu.stream().filter(t->t.getCalories()>500))
    }
}
