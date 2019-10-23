package com.ljljob.concurrent.executors;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/10/21 17:10
 * @Function:
 * @Version 1.0
 */
public class ThreadPoolExecutorCreateTest {
    private final static Random random = new Random(System.currentTimeMillis());

    /**
     * public ThreadPoolExecutor(
     * int corePoolSize,  # 最大线程保留存活数
     * int maximumPoolSize, # 线程池最多进程数
     * long keepAliveTime, # 线程闲置最长空闲时间
     * TimeUnit unit,      # 时间单位
     * BlockingQueue<Runnable> workQueue, # 任务队列,在当前线程沾满之后压入
     * ThreadFactory threadFactory,  # 传入包装一个Thread
     * RejectedExecutionHandler handler # 拒绝策略 当线程上线和Queue都饱和抛出的策略
     * )
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5), Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy());
        List<Callable<Integer>> callableList = IntStream.rangeClosed(1, 100).boxed().map(i -> (Callable<Integer>) () -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " -> " + i);
            return i;
        }).collect(toList());
        List<Future<Integer>> futures = executor.invokeAll(callableList);
        executor.awaitTermination(10,TimeUnit.SECONDS);
        for (Future<Integer> future : futures) {
            System.out.println("===========");
            System.out.println(future.get());
        }
    }
}
