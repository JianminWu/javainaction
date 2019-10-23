package com.ljljob.concurrent.executors;

import java.util.concurrent.*;

/**
 * @Author: wujianmin
 * @Date: 2019/10/22 9:58
 * @Function:
 * @Version 1.0
 * ThreadPoolExecutor 其他相关API
 * 1. {@link ThreadPoolExecutor#shutdown()} {@link ThreadPoolExecutor#shutdownNow()}
 * 调用shutdown之后线程会立刻被标记shutdown此时不能再提交task 已经提交的任务执行完成之后会结束生命周期
 * shutdownNow 会立刻打断正在运行的任务
 * 2. {@link ThreadPoolExecutor#isTerminated()} {@link ThreadPoolExecutor#isTerminating()}
 * 判断线程池生命周期是否结束,调用shutdown之后线程会进入terminating状态正在结束,等所有任务
 * 调度完成之后会变成terminated状态线程结束
 * 3. {@link ThreadPoolExecutor#awaitTermination(long, TimeUnit)} 当前线程阻塞，直到等所有已提交的任务（包括正在跑的和队列中等待的）执行完 或者等超时时间到 或者线程被中断，抛出InterruptedException
 * true -> if this executor terminated  如果已经结束
 * false -> if the timeout elapsed before termination 如果超时返回false
 * 4.{@link ThreadPoolExecutor#allowCoreThreadTimeOut(boolean)}
 * 允许线程池回收空闲的coreSize线程
 * 5.{@link ThreadPoolExecutor#remove(Runnable)} 删除队列中的runnable 不能移除一定调度执行的runnable
 * 6.{@link ThreadPoolExecutor#purge()} 清空任务队列queue
 * 7.{@link ThreadPoolExecutor#beforeExecute(Thread, Runnable)} | {@link ThreadPoolExecutor#afterExecute(Runnable, Throwable)}
 * 调度任务之前执行和调度任务之后执行(包可见)
 */
public class ThreadPoolExecutorExample {
    public static void main(String[] args) throws InterruptedException {
//        shutdownAndIsShutdown();
//        testIsTerminated();
//        testAwaitTermination();
//        testAllowCoreThreadTimeOut();
//        testRemove();
        beforeAndAfterExecute();
    }

    /**
     * 调用shutdown之后 executorService会立刻标记为shutdown 此时不能再提交任务.提交任务会被拒绝
     * 但程序并没有退出,等待线程池所有任务执行完成之后退出
     */
    public static void shutdownAndIsShutdown() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println(executorService.isShutdown());
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        // if submit task after shutdown will throw RejectedExecutionException
//        executorService.submit(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        System.out.println(executorService.isShutdown());
    }

    public static void testIsTerminated() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminating());
        System.out.println(executorService.isTerminated());
        TimeUnit.SECONDS.sleep(15);
        System.out.println(executorService.isTerminated());
    }

    public static void testAwaitTermination() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("I am come from threadPoolExecutor");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
        System.out.println("Thread is be interrupted");
    }


    public static void testAllowCoreThreadTimeOut() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy());
//        executor.allowCoreThreadTimeOut(false);
        executor.allowCoreThreadTimeOut(true);
        System.out.println(executor.getPoolSize());
        executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("I am come from threadPoolExecutor");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(executor.getPoolSize());

        TimeUnit.SECONDS.sleep(4);
        System.out.println(executor.getPoolSize());
    }


    public static void testRemove() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("I come from submit ");
        });
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("I come from remove ");
            }
        };
        executorService.remove(r);
    }

    public static void beforeAndAfterExecute() throws InterruptedException {
        MyThreadPoolExecutor executor = new MyThreadPoolExecutor(1, 2, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3), Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy());
        executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("I am come from threadPoolExecutor "+ Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        executor.awaitTermination(5,TimeUnit.SECONDS);
    }

    static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            super.beforeExecute(t, r);
            System.out.println("before starting " + Thread.currentThread().getName());
        }

        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            super.afterExecute(r, t);
            if(null!=t){
                System.out.println("task is error!");
            }else{
                System.out.println("finish task " + Thread.currentThread().getName());
            }
        }
    }

    public static void pushTaskToQueue() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        executorService.getQueue().put(() -> {
            System.out.println("hehehe");
        });
    }
}
