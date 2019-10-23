package com.ljljob.designer.feature;



import java.util.function.Consumer;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 15:39
 * @Function:
 * @Version 1.0
 */
public class FeatureService<T> {

    public Feature<T> submit(final FeatureTask<T> task) {
        AsynFeature<T> future = new AsynFeature<>();
        new Thread(() -> {
            T result = task.call();
            future.done(result);
        }).start();
        return future;
    }

    public Feature<T> submit(final FeatureTask<T> task, Consumer<T> consumer) {
        AsynFeature<T> future = new AsynFeature<>();
        new Thread(() -> {
            T result = task.call();
            future.done(result);
            consumer.accept(result);
        }).start();
        return future;
    }
}
