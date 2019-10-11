package com.ljljob.classloader.chapter3;

import com.ljljob.classloader.chapter2.MyClassLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author: wujianmin
 * @Date: 2019/10/11 15:21
 * @Function:
 * @Version 1.0
 */
public class EncryptClassloaderTest {

//    public static void main(String[] args) throws ClassNotFoundException {
//        //使用普通ClassLoader
//        // ClassFormatError class文件校验失败 魔术因子不正确
//        // java.lang.ClassFormatError: Incompatible magic value 3422534591 in class file SimpleObject
//        MyClassLoader myClassLoader = new MyClassLoader("SimpleObject");
//        myClassLoader.setDir("D:\\test_class_load\\chatper3\\");
//        Class<?> o = myClassLoader.loadClass("SimpleObject");
//    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        EncryptClassloader encryptClassloader = new EncryptClassloader("encryptClassLoader");

        Class<?> aClass = encryptClassloader.findClass("com.ljljob.classloader.chapter2.SimpleObject");
        Object o = aClass.newInstance();
        Method hello = aClass.getMethod("hello");
        hello.invoke(o, null);

    }
}
