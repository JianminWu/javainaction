package com.ljljob.designer.feature;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/11 17:25
 * @Function:
 * @Version 1.0
 */
public class ExecutorFeatureTest {
    private static AtomicInteger index = new AtomicInteger(1);

    private final static Random random = new Random();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5,(r)->{
            Thread t = new Thread(r);
            t.setDaemon(false);
            return t;
        });
//        IntStream.rangeClosed(1,10).forEach(i->{
//            Feature<Integer> future = executorService.submit(() -> {
//                int andIncrement = index.getAndIncrement();
//                try {
//                    Thread.sleep(random.nextInt(1000));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(andIncrement);
//                return andIncrement;
//            });
//        });
        CountDownLatch countDownLatch = new CountDownLatch(10);
        List<Integer> allResult= Lists.newArrayList();
        IntStream.rangeClosed(1,10).forEach(i->{
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
                int andIncrement = index.getAndIncrement();
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(andIncrement);
                return andIncrement;
            }, executorService);
            completableFuture.whenCompleteAsync((t,e)->{
                allResult.add(t);
                countDownLatch.countDown();
            });
        });
        countDownLatch.await();
        System.out.println(allResult);
        executorService.shutdown();
    }
}
