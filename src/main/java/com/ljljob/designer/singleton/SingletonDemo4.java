package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 DoubleCheck 但可能会出现NullPointException
 *
 * @Version 1.0
 */
public class SingletonDemo4 {

    private SingletonDemo4() {
        // 如果在私有构造函数充 实例化的了其他类,在调用getinstance获取实例的时候,其他类还没加载好,报空指针
        // empty
        // do some heavy things
        // int i=0, j=10;
        // obj = new Object();
        // ...
    }

    private static SingletonDemo4 instance;

    public static SingletonDemo4 getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo3.class) {
                if (instance == null) {
                    instance = new SingletonDemo4();
                }
            }
        }
        return instance;
    }
}
