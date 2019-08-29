package com.ljljob.action;

import com.sun.org.apache.xpath.internal.SourceTree;
import jdk.nashorn.internal.objects.annotations.Getter;

import java.util.*;
import java.util.stream.Collector;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/8/27 10:30
 * @Function:
 * @Version 1.0
 */
public class CollectorsTest {
    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
//        testAverage();
//        testCollectingAndThen();
//        testCounting();
//        testGroupBy();
//        testGroupByWithDownstream();
//        testJoin();
//        testJoinWithSpliter();
//        testJoinWithDelimitAndPrefixAndSuffix();
//        testMapping();
        testMaxByAndMinBy();
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

    public static void testCounting() {
        Optional.ofNullable(menu.stream().filter(t -> t.getCalories() > 500));
    }

    public static void testGroupBy() {
        Optional.of(menu.stream().collect(Collectors.groupingBy(Dish::getType))).ifPresent(System.out::println);
    }

    public static void testGroupByWithDownstream() {
        Map<Dish.Type, Long> result1 = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));

        Map<Dish.Type, Integer> result = menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testGroupByWithDownstreamAndSupplier() {
//        menu.stream().collect(Collectors.groupingBy(Dish::getType,LinkedList::new,Collectors.summingInt(Dish::getCalories)));
    }

    public static void testJoin() {
        String result = menu.stream().map(Dish::getName).collect(Collectors.joining());
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testJoinWithSpliter() {
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(","))).ifPresent(System.out::println);
    }

    public static void testJoinWithDelimitAndPrefixAndSuffix() {
        Optional.ofNullable(menu.stream().map(Dish::getName).collect(Collectors.joining(",", "[", "]"))).ifPresent(System.out::println);
    }

    public static void testMapping() {
        Map<Object, Long> result = menu.stream().map(t -> {
            HashMap map = new HashMap();
            map.put(t.getType(), 1);
            return map;
        }).collect(Collectors.groupingBy(t -> t.keySet().toArray()[0], Collectors.counting()));
//        Integer result = menu.stream().collect(Collectors.mapping((t) -> t, Collectors.summingInt(Dish::getCalories)));
//        menu.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(",")));
//        Integer result = menu.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summingInt(Integer::valueOf)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testMapping2(){
        Integer result = menu.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summingInt(Integer::valueOf)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testMaxByAndMinBy(){
        menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
        menu.stream().collect(Collectors.minBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
    }
}
