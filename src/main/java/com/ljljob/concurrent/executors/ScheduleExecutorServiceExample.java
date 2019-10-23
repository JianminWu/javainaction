package com.ljljob.concurrent.executors;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/23 11:32
 * @Function:
 * @Version 1.0
 * ScheduleThreadPoolExecutor
 * 1. create new ScheduleThreadPoolExecutor(int coreSize)
 * 2. {@link ScheduledThreadPoolExecutor#schedule(Runnable, long, TimeUnit)}running a task after await some time (delay)
 * 3. {@link ScheduledThreadPoolExecutor#scheduleAtFixedRate(Runnable, long, long, TimeUnit)} run a runnable or callable on a fixed period
 * Notice: If task elapse more than rate ,ScheduleExecutor Like {@link java.util.Timer} Must Wait for completed.
 * ScheduleThreadPoolExecutor 在当任务执行时长大于,间隔时间的时候,跟Timer一样会等到任务执行完成之后再调度下一个任务
 * Quartz Or Linux CornTab is Frequency First
 * 4. {@link ScheduledThreadPoolExecutor#setExecuteExistingDelayedTasksAfterShutdownPolicy(boolean)}
 * 设置如果在设置了delay任务之后如果被shutdown了之后还会不会继续执行 true 执行 false不执行 Default is false
 * 5. {@link ScheduledThreadPoolExecutor#setContinueExistingPeriodicTasksAfterShutdownPolicy(boolean)}
 * 设置 shutdown之后周期任务还会不会继续执行 true 继续执行 false不继续执行 Default is false
 * 6. {@link ScheduledThreadPoolExecutor#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)}
 * 无论任务多久完成,完成之后固定delay固定的一个时间后继续执行
 */
public class ScheduleExecutorServiceExample {

    public static void main(String[] args) throws InterruptedException {
//        testDelay();
//        testRate();
//        testRateIfTaskRunningMoreThanRate();
//        testSetExecuteExistingDelayedTasksAfterShutdownPolicy();
//        testSetContinueExistingPeriodicTasksAfterShutdownPolicy();
        testScheduleWithFixedDelay();
    }

    public static void testDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.schedule(() -> System.out.println(System.currentTimeMillis()), 2, TimeUnit.SECONDS);
    }

    public static void testRate() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleAtFixedRate(() -> System.out.println(System.currentTimeMillis()), 2, 2, TimeUnit.SECONDS);
    }

    public static void testRateIfTaskRunningMoreThanRate() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis());
        };
        executor.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
    }

    public static void testSetExecuteExistingDelayedTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
//        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
        executor.schedule(() -> System.out.println(System.currentTimeMillis()), 2, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(20);
        executor.shutdown();
    }

    public static void testSetContinueExistingPeriodicTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
//        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        Runnable runnable = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(System.currentTimeMillis());
        };
        executor.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);
        TimeUnit.MILLISECONDS.sleep(20);
        executor.shutdown();
    }

    public static void testScheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        Runnable runnable = () -> {
            try {
                System.out.println("start time ->" +System.currentTimeMillis());
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end time ->" +System.currentTimeMillis());
        };
        executor.scheduleWithFixedDelay(runnable,0,2,TimeUnit.SECONDS);
    }
}
