package com.ljljob.classloader.chapter1;

import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/10/9 10:06
 * @Function:
 * @Version 1.0
 */
public class Child extends Parent {

    public static final int child_final_static_x = 0;
    public static final int child_final_static_random_x = new Random().nextInt(100);

    public static int child_x = 0;

    static{
        System.out.println("child has loaded");
    }
}
