package com.ljljob.concurrent.executors;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Author: wujianmin
 * @Date: 2019/10/21 10:04
 * @Function:
 * @Version 1.0
 */
public class ExecutorServiceTest {

    public static void main(String[] args) {
//        useCacheExecutor();
//        useFixExecutor();
//        useSingleExecutor();
//        useStealingPoll();
    }

    /**
     * new ForkJoinPool
     * (Runtime.getRuntime().availableProcessors(),
     * ForkJoinPool.defaultForkJoinWorkerThreadFactory,
     * null, true)
     * 通过ForkJoinPool实现
     * feature
     * 1. 默认并发数为CPU核数
     * 2. A work-stealing pool makes no
     * guarantees about the order in which submitted tasks are
     * executed. 不保证按提交任务的顺序执行
     * 3. 合理利用多核CPU 适合耗时独立任务
     * 4. 一般使用callable任务
     */
    public static void useStealingPoll() {
        ExecutorService executorService = Executors.newWorkStealingPool();
        ForkJoinPool forkJoinPool = (ForkJoinPool) executorService;
        System.out.println(forkJoinPool.getActiveThreadCount());
        List<Callable<Integer>> taskList = IntStream.rangeClosed(1, 10).boxed().map(i -> (Callable<Integer>) () -> {
            sleep(2);
            System.out.println(Thread.currentThread().getName() + " -> " + i);
            return i;
        }).collect(toList());
        try {
            List<Future<Integer>> futures = executorService.invokeAll(taskList);
            // invokeAll获取future中的结果会阻塞,待所有任务执行完毕之后全部返回
            for (Future<Integer> future : futures) {
                System.out.println("result ->" + future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * new FinalizableDelegatedExecutorService
     * (new ThreadPoolExecutor(1, 1,
     * 0L, TimeUnit.MILLISECONDS,
     * new LinkedBlockingQueue<Runnable>()));
     * <p>
     * feature:
     * 1. 本质上是ThreadPollExecutor的子类,但内部通过FinalizableDelegatedExecutorService包装了一次,不能转成本质上是ThreadPollExecutor的子类
     * 2. 创建一个最多一个线程的线程池,线程池存活线程为1个
     * 3. 超时时间为0
     * 4. 使用LinkedBlockingQueue保存Runnable
     * 与new Thread的区别
     * 1. SingleThread 会缓存Runnable Thread不会
     * 2. SingleThread 执行完成之后不会关闭 Thread会
     */
    public static void useSingleExecutor() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // Executors$FinalizableDelegatedExecutorService cannot be cast to java.util.concurrent.ThreadPoolExecutor
        // SingleThread不是ThreadPoolExecutor的子类,而是包可见的FinalizableDelegatedExecutorService,屏蔽了一些ThreadPoolExecutor中的方法
        IntStream.rangeClosed(1, 10).forEach(i -> {
            executorService.execute(() -> {
                sleep(2);
                System.out.println(Thread.currentThread().getName() + " -> " + i);
            });
        });
    }

    /**
     * new ThreadPoolExecutor(nThreads, nThreads,
     * 0L, TimeUnit.MILLISECONDS,
     * new LinkedBlockingQueue<Runnable>());
     * feature:
     * 1. 最多存活线程,线程池最多线程数相同
     * 2. 超时时间为0
     * 3. 线程队列
     * 使用指定固定线程大小线程池,超过线程最大数量的任务会放再LinkedBlockingQueue中
     * 执行完毕之后,不会自动关闭
     */
    public static void useFixExecutor() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executorService;
        System.out.println(threadPool.getActiveCount()); // 0
        IntStream.rangeClosed(1, 100).forEach(i -> {
            executorService.execute(() -> {
                sleep(2);
                System.out.println(Thread.currentThread().getName() + " -> " + i);
            });
        });
        System.out.println(threadPool.getActiveCount()); // 100
    }

    /**
     * new ThreadPoolExecutor(0, Integer.MAX_VALUE,
     * 60L, TimeUnit.SECONDS,
     * new SynchronousQueue<Runnable>());
     * feature
     * 1. 保持存活的最大线程为0
     * 2. 线程池中最大允许线程数 Interger.MAX_VALUE
     * 3. 默认超时时间为60s 如果超过60s线程为idel 自动销毁
     * 4. BlockingQueue() 只能保存一个线程的queue
     * cachedThreadPool 因为是BlockingQueue,且最大存活线程为0,每次有线程调度会重新开启一个新的线程
     * 在线程执行结束60s之后会被销毁,cachedThreadPoll所有任务调度完成之后会自动关闭(等待超时回收之后)
     * 适合执行许多小的业务hort-lived asynchronous tasks.
     */
    public static void useCacheExecutor() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) cachedThreadPool;
        System.out.println(threadPool.getActiveCount()); // 0
        IntStream.rangeClosed(1, 100).forEach(i -> {
            cachedThreadPool.execute(() -> {
                sleep(2);
                System.out.println(Thread.currentThread().getName() + " -> " + i);
            });
        });
        System.out.println(threadPool.getActiveCount()); // 100
    }


    public static void sleep(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


