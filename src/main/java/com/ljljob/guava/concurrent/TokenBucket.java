package com.ljljob.guava.concurrent;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;
import com.ljljob.designer.context.QueryFromHttpAction;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 10:25
 * @Function:
 * @Version 1.0
 */
public class TokenBucket<T> {

    private final ConcurrentLinkedQueue<T> container;

    private final int DEFAULT_MAX_CONTAINER_SIZE = 1000;

    private final RateLimiter limiter = RateLimiter.create(10);

    private final Monitor putMonitor = new Monitor();

    private final Monitor pollMonitor = new Monitor();

    public TokenBucket(ConcurrentLinkedQueue<T> container) {
        this.container = container;
    }

    public void put(T t ){
        Stopwatch started = Stopwatch.createStarted();
        boolean success = limiter.tryAcquire(10, TimeUnit.SECONDS);
        if(success){
            if(putMonitor.enterIf(putMonitor.newGuard(()->container.size()<DEFAULT_MAX_CONTAINER_SIZE))){
                try {
                    System.out.println("container add ->" + t);
                    container.add(t);
                } finally {
                    putMonitor.leave();
                }
            }else{
                throw new IllegalStateException(" bucket is full.");
            }
        }else{
            started.stop();
        }

    }

    public T take(Consumer<T> consumer){
        if(pollMonitor.enterIf(pollMonitor.newGuard(()->!container.isEmpty()))){
            T result;
            try {
                result = container.poll();
                consumer.accept(result);
            } finally {
                pollMonitor.leave();
            }
            return result;
        }
        return null;
    }
}
