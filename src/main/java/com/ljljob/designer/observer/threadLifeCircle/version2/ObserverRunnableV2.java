package com.ljljob.designer.observer.threadLifeCircle.version2;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 15:15
 * @Function:
 * @Version 1.0
 */
public class ObserverRunnableV2 extends Thread {
    private ObserverListener2 listener;

    private Runnable runnable;

    public ObserverRunnableV2(ObserverListener2 listener, Runnable runnable) {
        this.listener = listener;
        this.runnable = runnable;
    }


    public enum ThreadState {
        RUNNING, DONE, ERROR
    }


    @Override
    public void run() {
        notifyObserver(new ThreadEvent(Thread.currentThread(), ThreadState.RUNNING, null));
        try {
            runnable.run();
        } catch (Exception e) {
            notifyObserver(new ThreadEvent(Thread.currentThread(), ThreadState.ERROR, e));
        }
        notifyObserver(new ThreadEvent(Thread.currentThread(), ThreadState.DONE, null));
    }

    public class ThreadEvent {
        private Thread thread;
        private ThreadState state;
        private Throwable cause;

        public ThreadEvent(Thread thread, ThreadState state, Throwable cause) {
            this.thread = thread;
            this.state = state;
            this.cause = cause;
        }

        public Thread getThread() {
            return thread;
        }

        public ThreadState getState() {
            return state;
        }

        public Throwable getCause() {
            return cause;
        }
    }

    public void notifyObserver(ThreadEvent event) {
        listener.onEvent(event);
    }

}
