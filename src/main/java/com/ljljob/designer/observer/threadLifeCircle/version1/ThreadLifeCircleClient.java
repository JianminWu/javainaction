package com.ljljob.designer.observer.threadLifeCircle.version1;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 11:45
 * @Function:
 * @Version 1.0
 */
public class ThreadLifeCircleClient {
    public static void main(String[] args) {
        ThreadLifeCircleListener listener = new ThreadLifeCircleListener();

        IntStream.rangeClosed(0, 5).forEach(i -> {
            new Thread(new ObserverRunnable(listener) {

                @Override
                public void run() {
                    try {
                        this.notifyObserver(new ObserverRunnableEvent(Thread.currentThread(), ThreadState.RUNNING, null));
                        int a = 1 / 0;
                        System.out.println("do some query things " + i);
                        this.notifyObserver(new ObserverRunnableEvent(Thread.currentThread(), ThreadState.DONE, null));
                    } catch (Exception e) {
//                        e.printStackTrace();
                        this.notifyObserver(new ObserverRunnableEvent(Thread.currentThread(), ThreadState.ERROR, e));
                    }
                }
            }, String.valueOf(i)).start();
        });

    }
}
