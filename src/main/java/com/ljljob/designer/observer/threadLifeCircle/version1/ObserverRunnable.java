package com.ljljob.designer.observer.threadLifeCircle.version1;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 10:42
 * @Function:
 * @Version 1.0
 */
public abstract class ObserverRunnable implements Runnable {

    private RunnableListener listeners;

    public ObserverRunnable(RunnableListener listeners) {
        this.listeners = listeners;
    }

    public enum ThreadState {
        FREE, RUNNING, BLOCK, DEAD, ERROR, DONE
    }

    protected static class ObserverRunnableEvent {
        private final Thread thread;
        private final ThreadState state;
        private final Throwable cause;

        public ObserverRunnableEvent(Thread thread, ThreadState state, Throwable cause) {
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


    void notifyObserver(ObserverRunnableEvent event) {
        listeners.onEvent(event);
    }
}
