package com.ljljob.action;

import java.awt.*;

/**
 * @Author: wujianmin
 * @Date: 2019/8/29 17:03
 * @Function:
 * @Version 1.0
 */
public class DefaultDemo {

    private interface A{
        default void hello(){
            System.out.println("===A===");
        }
    }
    private interface B extends A{
        @Override
        default void hello() {
            System.out.println("===B===");
        }
    }

    private static class C implements B{
        @Override
        public void hello() {
            System.out.println("===C===");
        }
    }


    public static void main(String[] args) {
        A c = new C();
        c.hello();

    }

}
