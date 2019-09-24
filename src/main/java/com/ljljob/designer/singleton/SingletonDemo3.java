package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 多线程使用synchronized 加锁,但是出现性能问题,每次调用都需要加锁,读写都加锁了
 * @Version 1.0
 */
public class SingletonDemo3 {

    private SingletonDemo3() {
        // empty
    }

    private static SingletonDemo3 instance;

    public synchronized static SingletonDemo3 getInstance() {
        if (instance == null) {
            instance = new SingletonDemo3();
        }
        return instance;
    }
}
