package com.ljljob.designer.countDown;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:38
 * @Function:
 * @Version 1.0
 */
public class MyCountDown {

    private int total;

    private int count;

    public MyCountDown(int total) {
        this.total = total;
        this.count = 0;
    }

    public synchronized void countDown() {
        this.count++;
        this.notifyAll();
    }

    public synchronized void await() throws InterruptedException {
        while (total!=count){
            this.wait();
        }
    }
}
