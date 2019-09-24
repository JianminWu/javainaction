package com.ljljob.thread.customeLock;

import java.util.Collection;

/**
 * @Author: wujianmin
 * @Date: 2019/9/19 16:29
 * @Function:
 * @Version 1.0
 */
public interface Lock {

    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock();

    void lock(long mills) throws TimeOutException;

    void unlock();

    Collection<Thread> getBlockThread();

    long getBlockThreadSize();
}
