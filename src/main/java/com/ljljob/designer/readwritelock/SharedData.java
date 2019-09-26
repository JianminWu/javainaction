package com.ljljob.designer.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 10:57
 * @Function:
 * @Version 1.0
 */
public class SharedData {
    private final char[] buffer;
    private final ReadWriteLock lock;

    private final java.util.concurrent.locks.ReadWriteLock LOCK;

    public SharedData(int size) {
        this(size, true);
    }

    public SharedData(int size, boolean prefWrite) {
        this.lock = new ReadWriteLock(prefWrite);
        this.LOCK = new ReentrantReadWriteLock();
        this.buffer = new char[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = '*';
        }
    }

    public String read() throws Exception {
//        this.lock.readLock();
        LOCK.readLock().lock();
        try {
            return this.doRead();
        } finally {
//            slowly(5);
            LOCK.readLock().unlock();
        }
    }

    private void slowly(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write(char c) throws InterruptedException {
        LOCK.writeLock().lock();
//        lock.writeLock();
        try {
            doWrite(c);
//            slowly(5);
        } finally {
            LOCK.writeLock().unlock();
//            lock.writeUnlock();
        }
    }

    private String doRead() {
        char[] newBuf = new char[buffer.length];
        System.arraycopy(buffer, 0, newBuf, 0, buffer.length);
        return String.valueOf(newBuf);
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = c;
        }
    }
}