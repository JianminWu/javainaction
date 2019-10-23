package com.ljljob.concurrent.executors;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/23 10:23
 * @Function:
 * @Version 1.0
 *
 *  1.{@link Future#cancel(boolean)}
 *    mayInterruptIfRunning 决定是否打断已经执行的任务,被打断之后不能调用get
 *    可能会失败 -> 1.1 task has already completed
 *             -> 1.2 has already been cancelled
 *             -> 1.2 has already for other reason
 *     If the task has already started,
 *      then the {@code mayInterruptIfRunning} parameter determines
 *      whether the thread executing this task should be interrupted in
 *      an attempt to stop the task.
 *     If task has already cancel ,can not feature.get(). CancellationException
 *   2.{@link Future#isDone} 正常结束|异常|被取消
 *   Completion may be due to normal termination, an exception, or
 *   cancellation -- in all of these cases, this method will return
 *   3.{@link Future#isCancelled()} if this task was cancelled before it completed
 *
 */
public class FeatureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        testFutureCancel();
    }

    public static void testFutureCancel() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " ====  ");
            return 5;
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println(future.cancel(true));
        System.out.println(future.isCancelled());
        System.out.println(future.isDone());
//        System.out.println(future.get());
    }

    public static void testFutureListCancle() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Integer>> features = Lists.newArrayList();
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            Future<Integer> future = executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " ====  " + i);
                return i;
            });
            features.add(future);
        });
        features.get(1).cancel(true);
    }
}
