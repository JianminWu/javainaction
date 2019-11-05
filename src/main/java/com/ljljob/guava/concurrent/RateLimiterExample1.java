package com.ljljob.guava.concurrent;

import com.google.common.util.concurrent.RateLimiter;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 9:54
 * @Function:
 * @Version 1.0
 */
public class RateLimiterExample1 {

    private static final RateLimiter limiter = RateLimiter.create(0.5);

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).boxed().forEach(i -> {
            new Thread(() -> {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " -> " + limiter.acquire());
                }
            }).start();
        });
    }
}
