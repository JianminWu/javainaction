package com.ljljob.guava.concurrent;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 10:00
 * @Function:
 * @Version 1.0
 */
public class BucketExample1<T> {

    private final ConcurrentLinkedQueue<T> queue;

    private final int MAX_SIZE = 1000;

    private final int DEFAULT_LIMIT = 10;

    private final RateLimiter limiter = RateLimiter.create(DEFAULT_LIMIT);

    private final Monitor putMonitor = new Monitor();

    private final Monitor pollMonitor = new Monitor();


    public BucketExample1(ConcurrentLinkedQueue queue) {
        this.queue = queue;
    }

    public void put(T t) {
        if (putMonitor.enterIf(putMonitor.newGuard(() -> queue.size() < MAX_SIZE))) {
            // 限流
            try {
                queue.add(t);
                System.out.println(currentThread() + " submit data " + t.toString() + ",current size:" + queue.size());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                putMonitor.leave();
            }
        } else {
            // 降级
            throw new IllegalStateException(" bucket is full.");
        }
    }

    public T take(Consumer<T> consumer)  {
        if (pollMonitor.enterIf(pollMonitor.newGuard(() -> !queue.isEmpty()))) {
            try {
                System.out.println(currentThread() + " waiting " + limiter.acquire());
                T result = queue.poll();
                consumer.accept(result);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pollMonitor.leave();
            }
        }
        return null;
    }

}
