package com.ljljob.action;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @Author: wujianmin
 * @Date: 2019/8/30 11:46
 * @Function:
 * @Version 1.0
 */
public class CompletableFutureHandlerDEMO {

    public static void main(String[] args) throws Exception{
        List<Dish> menu = Dish.menu;
        ExecutorService executor = Executors.newFixedThreadPool(2,(r)->{
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });
        CompletableFuture<List<Integer>> result = CompletableFuture.supplyAsync(() -> menu.stream().map(Dish::getCalories).collect(Collectors.toList()))
                .thenApply(i -> i.stream().map(j -> j * 10).collect(Collectors.toList()))
                .handle((v, t) -> {
//                    Optional.ofNullable(v).ifPresent(System.out::println);
                    return v;
                });
        List<Integer> r = result.get();
        System.out.println(r);
        executor.shutdown();
    }
}
