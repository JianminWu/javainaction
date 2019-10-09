package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 16:18
 * @Function:
 * @Version 1.0
 */
public class ActiveObjectsFactory {

    private ActiveObjectsFactory() {
    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActiveObjectQueue activeObjectQueue = new ActiveObjectQueue(10);
        ScheduleThread scheduleThread = new ScheduleThread(activeObjectQueue);
        ActiveObjectProxy activeObjectProxy = new ActiveObjectProxy(servant, scheduleThread);
        scheduleThread.start();
        return activeObjectProxy;
    }

}
