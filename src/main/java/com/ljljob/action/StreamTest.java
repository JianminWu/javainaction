package com.ljljob.action;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/8/27 9:56
 * @Function:
 * @Version 1.0
 */
public class StreamTest {
    private static List<Dish> menu = Dish.menu;


    public static void main(String[] args) {
        testFilter();
        testMap();
        testReduce();
        testSort();
    }

    public static void testFilter() {
        List<Dish> result = menu.stream().filter(t -> t.getCalories() > 300).collect(toList());
//        Optional.ofNullable(result).ifPresent(System.out::print);
        result.stream().forEach(System.out::println);
        System.out.println("========================");
    }

    public static void testMap() {
        List<String> result = menu.stream().filter(t -> t.getCalories() > 400).map(t -> t.getName()).collect(toList());
        Optional.ofNullable(result).ifPresent(System.out::println);
        System.out.println("========================");
    }

    public static void testReduce() {
        menu.stream().map(Dish::getCalories).reduce((a, b) -> a + b).ifPresent(System.out::println);
        System.out.println("========================");
    }

    public static void testSort() {
        List<Dish> result = menu.stream().sorted(Comparator.comparing(Dish::getCalories).reversed()).collect(toList());
        Optional.ofNullable(result).ifPresent(System.out::println);
        System.out.println("========================");

    }


}
