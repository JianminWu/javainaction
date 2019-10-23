package com.ljljob.concurrent.juc.forkjoin;

import java.util.Calendar;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/17 19:50
 * @Function:
 * @Version 1.0
 * ForkJoin 有返回值的实现RecursiveTask
 *  分而治之
 */
public class RecursiveTaskTest {

    private static final int MAX_SIZE = 50;

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> feature = forkJoinPool.submit(new CalculateRecursiveTask(1, 1000));
        try {
            Optional.ofNullable(feature.get()).ifPresent(System.out::println);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class CalculateRecursiveTask extends RecursiveTask<Integer> {
        private final int start;
        private final int end;

        public CalculateRecursiveTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if ((start - end) < MAX_SIZE) {
                return IntStream.rangeClosed(start, end).sum();
            } else {
                int middle = (start + end) / 2;
                CalculateRecursiveTask leftTask = new CalculateRecursiveTask(start, middle);
                CalculateRecursiveTask rightTask = new CalculateRecursiveTask(middle + 1, end);
                leftTask.fork();
                rightTask.fork();
                return leftTask.join() + rightTask.join();
            }
        }
    }
}
