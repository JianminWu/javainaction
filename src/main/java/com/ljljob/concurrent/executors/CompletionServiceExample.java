package com.ljljob.concurrent.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/23 15:51
 * @Function:
 * @Version 1.0
 */
public class CompletionServiceExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        test1();
    }

    public static void test1() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorCompletionService completionService = new ExecutorCompletionService(executorService);
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            completionService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-> is finished +" + i);
                return i*10;
            });
        });
//        for (int i =1; i <= 5; i++) {
//            Future take = completionService.take();
//            System.out.println("result -> "+ take.get());
//        }
    }
}
