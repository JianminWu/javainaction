package com.ljljob.designer.observer.threadLifeCircle.version2;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 15:24
 * @Function:
 * @Version 1.0
 */
public class ObserverClientTest {

    public static void main(String[] args) {
        ThreadLifeCircleListener listener2 = new ThreadLifeCircleListener();

        IntStream.rangeClosed(0, 4).forEach(i -> {
            new ObserverRunnableV2(listener2, () -> {
                int a = 1 / 0;
                System.out.println("do some query things " + i);
            }).start();
        });
    }
}
