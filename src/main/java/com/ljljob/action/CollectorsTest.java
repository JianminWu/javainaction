package com.ljljob.action;

import com.sun.xml.internal.bind.v2.runtime.reflect.opt.OptimizedAccessorFactory;

import java.rmi.MarshalException;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
//        testAverage();
//        testCollectingAndThen();
//        testCounting();
//        testGroupBy();
//        testGroupByWithDownstream();
//        testJoin();
//        testJoinWithSpliter();
//        testJoinWithDelimitAndPrefixAndSuffix();
//        testMapping();
//        testMaxByAndMinBy();
//        testPartitioningBy();
//        testPartitioningByWithDownstream();
//        testReduce();
//        testReduceWithIndentifyAndBinaryOperator();
//        testSummarizing();
//        testSumming();
        testToMap();
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

    public static void testMapping2() {
        Integer result = menu.stream().collect(Collectors.mapping(Dish::getCalories, Collectors.summingInt(Integer::valueOf)));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testMaxByAndMinBy() {
        menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
        menu.stream().collect(Collectors.minBy(Comparator.comparing(Dish::getCalories))).ifPresent(System.out::println);
    }

    public static void testPartitioningBy() {
        Map<Boolean, List<Dish>> result = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testPartitioningByWithDownstream() {
        Map<Boolean, Integer> result = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300, Collectors.summingInt(Dish::getCalories)));
        Optional.ofNullable(result).ifPresent(System.out::println);
        Map<Boolean, Long> result2 = menu.stream().collect(Collectors.partitioningBy(t -> t.getCalories() > 300, Collectors.counting()));
        Optional.ofNullable(result2).ifPresent(System.out::println);
    }

    public static void testReduce() {
        Optional<Dish> max = menu.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories))));
        max.ifPresent(System.out::println);
        menu.stream().collect(Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(Dish::getCalories)))).ifPresent(System.out::println);
        menu.stream().map(Dish::getCalories).collect(Collectors.reducing(Integer::sum)).ifPresent(System.out::println);
    }

    public static void testReduceWithIdentify() {
        // identify 类似于一个init值
        menu.stream().map(Dish::getCalories).collect(Collectors.reducing(0, Integer::sum));
    }

    public static void testReduceWithIndentifyAndBinaryOperator() {
        Integer result = menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testSummarizing() {
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingInt(t -> Integer.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingDouble(t -> Double.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summarizingLong(t -> Long.valueOf(t.getCalories())))).ifPresent(System.out::println);
        LongSummaryStatistics result = menu.stream().collect(Collectors.summarizingLong(t -> Long.valueOf(t.getCalories())));
        result.getAverage();
    }

    public static void testSumming() {
        Optional.ofNullable(menu.stream().collect(Collectors.summingInt(Dish::getCalories))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summingDouble(t -> Double.valueOf(t.getCalories())))).ifPresent(System.out::println);
        Optional.ofNullable(menu.stream().collect(Collectors.summingLong(t -> Long.valueOf(t.getCalories())))).ifPresent(System.out::println);
    }

    public static void testToMap() {
//        Optional.ofNullable(menu.stream().collect(Collectors.toMap(Dish::getName, Function.identity()))).ifPresent(System.out::println);
        Map<Dish.Type, Integer> result = menu.stream().collect(Collectors.toMap(Dish::getType, Dish::getCalories, (pre, next) -> pre + next));
        Optional.ofNullable(result).ifPresent(System.out::println);
    }

    public static void testToConcurrentMap(){
        ConcurrentMap<Dish.Type, Integer> result = menu.stream().collect(Collectors.toConcurrentMap(Dish::getType, Dish::getCalories, Integer::sum));
        Optional.ofNullable(result).ifPresent(System.out::println);
        Optional.ofNullable(result.getClass()).ifPresent(System.out::println);
    }

}
