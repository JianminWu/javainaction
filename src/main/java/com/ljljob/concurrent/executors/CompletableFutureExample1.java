package com.ljljob.concurrent.executors;

import java.util.concurrent.*;

/**
 * @Author: wujianmin
 * @Date: 2019/10/23 16:50
 * @Function:
 * @Version 1.0
 */
public class CompletableFutureExample1 {

    public static void main(String[] args) throws InterruptedException {
        simpleExample1();
    }

    public static void simpleExample1() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            }
        });
        CompletableFuture.supplyAsync(()->{
           try {
               TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           return ThreadLocalRandom.current().nextInt(5);
       },executorService).thenApply(t->t*10).whenComplete((t,e)->{
           if (null!=e){
               e.printStackTrace();
           }
           System.out.println(t+1);
       });
        Thread.currentThread().join();
    }
}
