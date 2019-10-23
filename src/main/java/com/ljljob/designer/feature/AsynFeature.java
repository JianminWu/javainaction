package com.ljljob.designer.feature;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 15:37
 * @Function:
 * @Version 1.0
 */
public class AsynFeature<T> implements Feature<T> {
    private T result;

    private volatile boolean done = false;

    public void done(T t) {
        synchronized (this) {
            this.result = t;
            this.done = true;
            this.notifyAll();
        }

    }

    @Override
    public synchronized T get() throws InterruptedException {
        while (!done) {
            this.wait();
        }
        return this.result;
    }
}
