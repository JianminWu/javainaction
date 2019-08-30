package com.ljljob.action;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: wujianmin
 * @Date: 2019/8/30 9:53
 * @Function:
 * @Version 1.0
 */
public class CompletableFutureDemo {

    private static Random random = new Random();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2,(r)->{
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });

        CompletableFuture.supplyAsync(CompletableFutureDemo::getDouble,executor)
                .thenApply(i->i*10)
                .whenComplete((v,t)->{
                    Optional.ofNullable(v).ifPresent(System.out::println);
                });
        executor.shutdown();
    }

    public static Double getDouble(){
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  random.nextDouble();
    }
}
