package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:01
 * @Function:
 * @Version 1.0
 */
public abstract class MethodRequest<T> {

    private final Servant servant;

    private final Result<T> futureResult;

    public MethodRequest(Servant servant, Result<T> futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();
}
