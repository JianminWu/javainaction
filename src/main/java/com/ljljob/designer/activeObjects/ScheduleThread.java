package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:33
 * @Function:
 * @Version 1.0
 */
public class ScheduleThread extends Thread {

    private final ActiveObjectQueue activeObjectQueue;

    public ScheduleThread(ActiveObjectQueue activeObjectQueue) {
        this.activeObjectQueue = activeObjectQueue;
    }

    public void invoice(MethodRequest request) {
        this.activeObjectQueue.put(request);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.activeObjectQueue.take().execute();
        }
    }
}
