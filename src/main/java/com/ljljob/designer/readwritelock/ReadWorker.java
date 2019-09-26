package com.ljljob.designer.readwritelock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 10:51
 * @Function:
 * @Version 1.0
 */
public class ReadWorker extends Thread {

    private SharedData sharedData;

    public ReadWorker(SharedData sharedData,String name) {
        super(name);
        this.sharedData = sharedData;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String data = sharedData.read();
                System.out.println("The read thread [" + Thread.currentThread().getName() + "] print data : " + data);
                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
