package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 Enum枚举 利用类加载的机制保证线程安全且单例唯一
 * @Version 1.0
 */
public class SingletonDemo7 {

    private SingletonDemo7() {
        // empty
    }

    private enum Singleton {
        INSTANCE;

        private final SingletonDemo7 instance;

        Singleton() {
            // 枚举初始化加载只会执行一次
            this.instance = new SingletonDemo7();
        }

        public SingletonDemo7 getInstance() {
            return instance;
        }
    }


    public static SingletonDemo7 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

}
