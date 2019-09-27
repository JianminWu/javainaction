package com.ljljob.designer.feature;



import java.util.function.Consumer;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 15:39
 * @Function:
 * @Version 1.0
 */
public class FutureService<T> {

    public Future<T> submit(final FutureTask<T> task) {
        AsynFuture<T> future = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            future.done(result);
        }).start();
        return future;
    }

    public Future<T> submit(final FutureTask<T> task, Consumer<T> consumer) {
        AsynFuture<T> future = new AsynFuture<>();
        new Thread(() -> {
            T result = task.call();
            future.done(result);
            consumer.accept(result);
        }).start();
        return future;
    }
}
