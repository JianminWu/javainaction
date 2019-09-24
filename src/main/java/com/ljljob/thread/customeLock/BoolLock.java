package com.ljljob.thread.customeLock;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;

/**
 * @Author: wujianmin
 * @Date: 2019/9/19 16:29
 * @Function:
 * @Version 1.0
 */
public class BoolLock implements Lock {

    private boolean lockValue = false;

    private LinkedList<Thread> CollectionOfBlockedThread = Lists.newLinkedList();

    private Thread currentThread;

    @Override
    public synchronized void lock() {
        while (lockValue) {
            // if lock wait
            CollectionOfBlockedThread.addLast(Thread.currentThread());
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.lockValue = true;
        CollectionOfBlockedThread.clear();
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void lock(long mills) throws TimeOutException {
        if (mills < 0)
            this.lock();
        long endTime = System.currentTimeMillis() + mills;
        long hasRemaining = endTime - System.currentTimeMillis();
        while (lockValue){
            if(hasRemaining <= 0){
                throw new TimeOutException("Timeout Exception");
            }else {
                try {
                    CollectionOfBlockedThread.addLast(Thread.currentThread());
                    this.wait(mills);
                    hasRemaining = endTime - System.currentTimeMillis();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        while (lockValue) {
//            if (stopwatch.elapsed(TimeUnit.MILLISECONDS) <= mills) {
//                // time out
//                throw new TimeOutException("time out ");
//            } else {
//                // block
//                CollectionOfBlockedThread.addLast(Thread.currentThread());
//                try {
//                    this.wait(mills);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        this.lockValue = true;
        CollectionOfBlockedThread.clear();
        this.currentThread = Thread.currentThread();
    }

    @Override
    public synchronized void unlock() {
        if (currentThread == Thread.currentThread()) {
            Optional.of(Thread.currentThread().getName() + " release the lock")
                    .ifPresent(System.out::println);
            this.lockValue = false;
            this.notifyAll();
//            CollectionOfBlockedThread.removeLast()
        }
    }

    @Override
    public Collection<Thread> getBlockThread() {
        return Collections.unmodifiableList(CollectionOfBlockedThread);
    }

    @Override
    public long getBlockThreadSize() {
        return this.CollectionOfBlockedThread.size();
    }

}
