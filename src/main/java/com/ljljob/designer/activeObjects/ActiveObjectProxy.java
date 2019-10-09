package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 16:13
 * @Function:
 * @Version 1.0
 */
public class ActiveObjectProxy implements ActiveObject {
    private final Servant servant;

    private final ScheduleThread scheduleThread;

    public ActiveObjectProxy(Servant servant, ScheduleThread scheduleThread) {
        this.servant = servant;
        this.scheduleThread = scheduleThread;
    }


    @Override
    public Result makeString(int size, char c) {
        FutureResult<String> futureResult = new FutureResult<>();
        scheduleThread.invoice(new MakeStringRequest(servant, futureResult, size, c));
        return futureResult;
    }

    @Override
    public void displayStr(String text) {
        scheduleThread.invoice(new DisplayStrRequest(servant, text));
    }
}
