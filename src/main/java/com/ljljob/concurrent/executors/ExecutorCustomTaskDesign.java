package com.ljljob.concurrent.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/22 16:24
 * @Function:
 * @Version 1.0
 */
public class ExecutorCustomTaskDesign {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        IntStream.rangeClosed(1,5).boxed().forEach(i->{
            executorService.submit(new MyTask(i) {
                @Override
                public void init() {
                    System.out.println("init from "+ Thread.currentThread().getName());
                }

                @Override
                public void task() {
                    int j = 1/0;
                    System.out.println("I am doing work " + Thread.currentThread().getName());
                }

                @Override
                public void error(Throwable t) {
                    System.err.println(t.getMessage());
                }

                @Override
                public void done() {
                    System.out.println("finish from "+ Thread.currentThread().getName());
                }
            });
        });
        executorService.shutdown();
    }

    static abstract class MyTask implements Runnable {

        private final int no;

        public MyTask(int no) {
            this.no = no;
        }
        public abstract void init();

        public abstract void task();

        public abstract void error(Throwable t);

        public abstract void done();
        @Override
        public void run() {
            init();
            try {
                task();
            } catch (Exception e) {
                error(e);
            }finally {
                done();
            }
        }
    }
}
