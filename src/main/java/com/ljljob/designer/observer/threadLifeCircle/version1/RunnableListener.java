package com.ljljob.designer.observer.threadLifeCircle.version1;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:44
 * @Function:
 * @Version 1.0
 */
//@FunctionalInterface
public interface RunnableListener {


    void onEvent(ObserverRunnable.ObserverRunnableEvent event);

}
