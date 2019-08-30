package com.ljljob.action;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/8/30 15:38
 * @Function:
 * @Version 1.0
 */
public class CompletableFutureDemo3 {
    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) throws InterruptedException {
//        testThenRun();
//        testAccept();
//        testCombine();
        testCompose();
        Thread.currentThread().join();
    }

    /**
     * 所有任务结束之后调用thenRun执行逻辑 入参为Runable 收尾工作
     */
    public static void testThenRun() {
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i * 10)
                .whenComplete((v, t) -> System.out.println(v))
                .thenRun(() -> System.out.println("All task is Done"));
    }

    /**
     * 接受上流参数执行comsumer,没有返回值
     */
    public static void testAccept() {
        CompletableFuture.supplyAsync(() -> getDishCalories(menu))
                .thenApply(list -> list.stream().map(i -> i * 10).collect(toList()))
                .thenAccept(System.out::println);
    }

    /**
     * combine 编排 合并2个线程,Combine传入bifunction 参数1 线程1返回值,参数2 线程2的返回值
     */
    public static void testCombine() {
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i * 10)
                .thenCombine(
                        CompletableFuture.supplyAsync(() -> 2.0d),
                        (r1, r2) -> r1 + r2
                )
                .thenAccept(System.out::println);
    }

    /**
     * compose 编排 拿到上一个future结果 传入一个function<上一个返回结果,处理之后返回的结果>
     */
    public static void testCompose() {
        CompletableFuture.supplyAsync(() -> 1)
                .thenApply(i -> i * 10)
                .thenCompose(r -> CompletableFuture.supplyAsync(() -> r + 50))
                .thenAccept(System.out::println);
    }

    public static List<Integer> getDishCalories(List<Dish> list) {
        List<Integer> result = list.stream().map(Dish::getCalories).collect(toList());
        return result;
    }
}
