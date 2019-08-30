package com.ljljob.action;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/8/30 10:33
 * @Function:
 * @Version 1.0
 */
public class CompletableFutureDemo2 {

//    public static void main(String[] args) {
//        List<Dish> menu = Dish.menu;
//        ExecutorService executor = Executors.newFixedThreadPool(2,(r)->{
//            Thread t = new Thread(r);
//            t.setDaemon(false);
//            return t;
//        });
//        CompletableFuture.supplyAsync(()->CompletableFutureDemo2.getDishCalories(menu),executor)
//                .thenApply(caloriesList->caloriesList.stream().map(i->i*10))
//                .whenComplete((t,v)->{
//                    Optional.ofNullable(t.collect(toList())).ifPresent(System.out::println);
//                });
//        executor.shutdown();
//    }

    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        List<Integer> result = menu.stream()
                .map(dish -> CompletableFuture.supplyAsync(() -> getDishCalories2(dish)))
                .map(future -> future.thenApply(i -> i * 10))
//                .map(future->future.whenComplete((t,v)->{
//                    Optional.ofNullable(t).ifPresent(System.out::println);
//                }))
                .map(CompletableFuture::join)
                .collect(toList());
        System.out.println(result);
    }

    public static List<Integer> getDishCalories(List<Dish> list ){
        List<Integer> result = list.stream().map(Dish::getCalories).collect(toList());
        return result;
    }

    public static int getDishCalories2(Dish dish){
         return dish.getCalories();
    }

}


