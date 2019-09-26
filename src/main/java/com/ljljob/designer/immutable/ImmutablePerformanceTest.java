package com.ljljob.designer.immutable;


import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 10:10
 * @Function:
 * @Version 1.0
 */
public class ImmutablePerformanceTest {

    public static void main(String[] args) {
        // 112883 immutable
        // 111916 sync
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Thread> threads = new ArrayList<>();
//        ImmutableUser immutableUser = new ImmutableUser("name", 15);
        SyncUser syncUser = new SyncUser();
        syncUser.setName("name");
        syncUser.setAge(15);
        IntStream.rangeClosed(0, 4).forEach(i -> {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 2000000; j++) {
                    System.out.println(Thread.currentThread().getName() + " " + syncUser.toString());
                }
            });
            t.start();
            threads.add(t);
        });
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(elapsed);

    }
}
