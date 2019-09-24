package com.ljljob.designer.observer.threadLifeCircle.version1;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:55
 * @Function:
 * @Version 1.0
 */
public class ThreadLifeCircleListener implements RunnableListener {

    private Object LOCK = new Object();

    @Override
    public void onEvent(ObserverRunnable.ObserverRunnableEvent event) {
        synchronized (LOCK) {
            if (event.getCause() == null) {
                System.out.println("The runnable [" + event.getThread().getName() + "] data changed and status is " + event.getState());
            } else {
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
