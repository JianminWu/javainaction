package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 14:48
 * @Function:
 * @Version 1.0
 */
public class FutureResult<T> implements Result<T> {

    private Result<T> result;

    private boolean finish = false;

    public synchronized void done(Result result) {
        this.finish = true;
        this.result = result;
        this.notifyAll();
    }

    @Override
    public synchronized T getResult() {
        while (!finish) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this.result.getResult();
    }
}
