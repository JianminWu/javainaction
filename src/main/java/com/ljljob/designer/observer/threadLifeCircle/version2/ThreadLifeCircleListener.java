package com.ljljob.designer.observer.threadLifeCircle.version2;


/**
 * @Author: wujianmin
 * @Date: 2019/9/24 15:22
 * @Function:
 * @Version 1.0
 */
public class ThreadLifeCircleListener implements ObserverListener2 {
    private final Object LOCK = new Object();

    @Override
    public void onEvent(ObserverRunnableV2.ThreadEvent event) {

        synchronized (LOCK) {
            if (event.getCause() != null) {
                event.getCause().printStackTrace();
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
            } else {
                System.out.println("The runnable [" + event.getThread().getName() + "] data changed and status is " + event.getState());
            }
        }
    }
}
