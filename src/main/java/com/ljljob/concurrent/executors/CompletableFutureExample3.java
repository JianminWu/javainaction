package com.ljljob.concurrent.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

/**
 * @Author: wujianmin
 * @Date: 2019/10/24 15:53
 * @Function:
 * @Version 1.0
 * <p>
 * 1. {@link CompletableFuture#getNow(Object)} 如果没有执行完毕立刻返回传入的值
 * 2. {@link CompletableFuture#get(long, TimeUnit)} 根据是否超时获取
 * 3. {@link CompletableFuture#obtrudeValue(Object)} 强制返回一个值 方法返回值为void 需要调用future.get()
 * 4. {@link CompletableFuture#obtrudeException(Throwable)} 强制返回一个异常 方法返回值为void 调用future.get() 抛出异常
 * 5. {@link CompletableFuture#exceptionally(Function)} 如果上流出现exception function(throwable e) 捕获
 */
public class CompletableFutureExample3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
//        testGetNow();
//        testGet();
//        testObtrudeValue();
//        testObtrudeException();
        testException();
    }

    public static void testException(){
        CompletableFuture.supplyAsync(()->1/0).exceptionally((e)->{
            System.out.println(e.getMessage());
            return 111;
        }).whenComplete((t,e)-> System.out.println(t));
    }

    public static void testObtrudeException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "ddd");
        future.obtrudeException(new RuntimeException("error"));
        future.get();
    }

    public static void testObtrudeValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "ddd");
        future.obtrudeValue("123");
        System.out.println(future.get());
    }

    public static void testGetNow() {
        Integer now = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            return 111;
        }).getNow(444);
        System.out.println(now);
    }

    public static void testGet() throws InterruptedException, ExecutionException, TimeoutException {
        Integer now = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            return 111;
        }).get(2000, TimeUnit.MILLISECONDS);
        System.out.println(now);
    }

    public static void sleep(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
