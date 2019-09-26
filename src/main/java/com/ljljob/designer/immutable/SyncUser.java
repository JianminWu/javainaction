package com.ljljob.designer.immutable;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 10:09
 * @Function:
 * @Version 1.0
 */
public class SyncUser {

    private String name;

    private int age;

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized void setAge(int age) {
        this.age = age;
    }

    @Override
    public synchronized String toString() {
        return "SyncUser{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
