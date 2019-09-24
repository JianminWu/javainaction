package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 多线程会出现创建多份实例!!!
 * @Version 1.0
 */
public class SingletonDemo2 {

    private SingletonDemo2() {
        // empty
    }

    private static SingletonDemo2 instance;

    public static SingletonDemo2 getInstance() {
        if (instance == null) {
            instance = new SingletonDemo2();
        }
        return instance;
    }
}
