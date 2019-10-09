package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 14:33
 * @Function:
 * @Version 1.0
 */
public interface ActiveObject<T> {

    Result<String> makeString(int size, char c);

    void displayStr(String text);
}
