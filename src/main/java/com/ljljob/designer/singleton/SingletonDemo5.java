package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 DoubleCheck 但可能会出现NullPointException 使用volatile解决
 * 但是使用volatile取消JVM自定义优化,导致性能问题
 * @Version 1.0
 */
public class SingletonDemo5 {

    private volatile static SingletonDemo5 instance;


    private SingletonDemo5() {
        // 如果在私有构造函数充 实例化的了其他类,在调用getinstance获取实例的时候,其他类还没加载好,报空指针
        // empty
        // do some heavy things
        // int i=0, j=10;
        // obj = new Object();
        // ...
    }

    public static SingletonDemo5 getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo5.class) {
                if (instance == null) {
                    instance = new SingletonDemo5();
                }
            }
        }
        return instance;
    }
}
