package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 14:48
 * @Function:
 * @Version 1.0
 */
public class RealResult<T> implements Result<T> {

    private T result;

    public RealResult(T result) {
        this.result = result;
    }

    @Override
    public T getResult() {
        return result;
    }
}
