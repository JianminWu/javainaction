package com.ljljob.classloader.chapter2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: wujianmin
 * @Date: 2019/10/10 11:29
 * @Function:
 * @Version 1.0
 */
public class MyClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader("my classLoader");
        Class<?> clazz = classLoader.findClass("com.ljljob.classloader.chapter2.SimpleObject");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
        Method hello = clazz.getMethod("hello");
        hello.invoke(o);
    }
}
