package com.ljljob.designer.singleton;

/**
 * @Author: wujianmin
 * @Date: 2019/9/23 11:26
 * @Function: 懒加载 Enum枚举 利用类加载的机制保证线程安全且单例唯一
 * @Version 1.0
 */
public class EnumSingleToneDemo {

    private EnumSingleToneDemo() {
        // empty
    }

    private enum Singleton {
        INSTANCE;

        private final EnumSingleToneDemo instance;

        Singleton() {
            // 枚举初始化加载只会执行一次
            this.instance = new EnumSingleToneDemo();
        }

        public EnumSingleToneDemo getInstance() {
            return instance;
        }
    }


    public static EnumSingleToneDemo getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

}
