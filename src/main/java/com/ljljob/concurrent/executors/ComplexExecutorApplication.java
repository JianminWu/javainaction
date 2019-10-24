package com.ljljob.concurrent.executors;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/23 15:20
 * @Function:
 * @Version 1.0
 * Executor综合一些得案例
 * 需求或问题: 1.在executor被shutdown之后正确捕获到没有执行到的所有runnable任务,
 * 2.直接存shutdown里面拿出来的runnable不能执行直接使用
 * 解决方案: 不信任shutdownNow()之后返回的结果,自定义task实现runnable 添加success标志位,最终遍历所有success标志位为false
 */
public class ComplexExecutorApplication {
    public static void main(String[] args) throws InterruptedException {
        gracefulExample();
    }

    public static void gracefulExample() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        List<MyTask> taskList = Lists.newArrayList();
        IntStream.rangeClosed(1,5).boxed().map(i->new MyTask(i) {
            @Override
            void task() {
                System.out.println(Thread.currentThread().getName() + " --> " + i);
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(5000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).forEach(taskList::add);
        taskList.forEach(executorService::submit);
        TimeUnit.SECONDS.sleep(1);
        executorService.shutdownNow();
        taskList.stream().filter(t->!t.success).forEach(t-> System.out.println(t.getNo()));
        System.out.println("=====");
    }

    public static void nagetiveExample() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            executorService.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-> is finished +" + i);
            });
        });
        TimeUnit.SECONDS.sleep(4);
        // 第二个线程其实已经开始执行了,但是没有执行完毕(这里因为用了sleep模式,打断会被sleep捕获),但shutdown之后没有获取到这个线程,到时最终
        // 超时打断之后返回的任务不完全
        List<Runnable> runnables = executorService.shutdownNow();
        System.out.println(runnables.size());
    }

    static abstract class MyTask implements Runnable {

        private final int no;

        public int getNo() {
            return no;
        }

        boolean success = false;

        public boolean isSuccess() {
            return success;
        }

        public MyTask(int no) {
            this.no = no;
        }

        private void done() {
            this.success = true;
        }

        abstract void task();

        @Override
        public void run() {
            task();
            done();
        }
    }


}
