package com.ljljob.classloader.chapter1;

/**
 * @Author: wujianmin
 * @Date: 2019/10/9 10:05
 * @Function:
 * @Version 1.0
 */
public class Parent {

    public static int parent_x = 0;

    static {
        System.out.println("parent has loaded");
    }
}
