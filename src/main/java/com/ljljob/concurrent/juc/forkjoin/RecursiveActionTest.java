package com.ljljob.concurrent.juc.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/17 20:00
 * @Function:
 * @Version 1.0
 * ForkJoin 实现无返回值 {@link RecursiveAction}
 */
public class RecursiveActionTest {

    private static final AtomicInteger SUM = new AtomicInteger(0);

    private static final int MAX_SIZE = 50;


    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.submit(new CalculateRecursiveAction(1, 1000));
        forkJoinPool.awaitTermination(100, TimeUnit.MILLISECONDS);
        System.out.println(SUM.get());
    }

    static class CalculateRecursiveAction extends RecursiveAction {

        private final int start;
        private final int end;

        public CalculateRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if ((start - end) < MAX_SIZE) {
                SUM.addAndGet(IntStream.rangeClosed(start, end).sum());
            } else {
                int middle = (start - end) / 2;
                CalculateRecursiveAction left = new CalculateRecursiveAction(start, middle);
                CalculateRecursiveAction right = new CalculateRecursiveAction(middle + 1, end);
                // Action只能fork不能JOIN
                left.fork();
                right.fork();
            }
        }
    }
}
