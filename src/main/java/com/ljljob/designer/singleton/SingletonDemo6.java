package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 Holder 利用类加载的机制保证线程安全且单例唯一
 * @Version 1.0
 */
public class SingletonDemo6 {

    private SingletonDemo6() {
        // empty
    }

    private static class InstanceHolder {
        private final static SingletonDemo6 instance = new SingletonDemo6();

    }

    public static SingletonDemo6 getInstance() {
        return InstanceHolder.instance;
    }

}
