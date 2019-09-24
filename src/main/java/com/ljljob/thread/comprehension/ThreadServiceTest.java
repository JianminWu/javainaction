package com.ljljob.thread.comprehension;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/9/17 9:35
 * @Function:
 * @Version 1.0
 */
public class ThreadServiceTest {

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ThreadService threadService = new ThreadService();
        threadService.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadService.setTimeOut(4000L);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }
}
