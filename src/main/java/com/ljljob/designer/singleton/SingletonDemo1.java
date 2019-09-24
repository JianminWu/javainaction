package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 饿汉式
 * @Version 1.0
 */
public class SingletonDemo1 {

    private SingletonDemo1() {
        // empty
    }

    private static SingletonDemo1 instance = new SingletonDemo1();

    public static SingletonDemo1 getInstance() {
        return instance;
    }
}
