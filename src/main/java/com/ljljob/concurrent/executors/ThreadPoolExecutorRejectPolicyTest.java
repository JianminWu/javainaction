package com.ljljob.concurrent.executors;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/22 16:45
 * @Function:
 * @Version 1.0
 * {@link ThreadPoolExecutor.AbortPolicy} 如果超出抛出Reject异常
 */
public class ThreadPoolExecutorRejectPolicyTest {

    public static void main(String[] args) throws InterruptedException {
//        testAbortPolicy();
//        testDiscardPolicy();
    }

    /**
     *
     */
    public static void testAbortPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
        });
        executorService.shutdown();
    }

    public static void testDiscardPolicy() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.DiscardPolicy());
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
        });
        executorService.shutdown();
    }


}
