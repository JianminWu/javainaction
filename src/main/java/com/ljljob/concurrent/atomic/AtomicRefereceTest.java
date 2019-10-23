package com.ljljob.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author: wujianmin
 * @Date: 2019/10/14 16:55
 * @Function:
 * @Version 1.0
 */
public class AtomicRefereceTest {

    private static AtomicReference<User> user = new AtomicReference<>();

    public static void main(String[] args) {
        user.set(new User("jim", 15));
    }

    static class User {
        private String name;

        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
