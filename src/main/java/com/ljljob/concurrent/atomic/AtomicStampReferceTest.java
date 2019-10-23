package com.ljljob.concurrent.atomic;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/14 17:14
 * @Function:
 * @Version 1.0
 */
public class AtomicStampReferceTest {


    public static void main(String[] args) {
        AtomicStampedReference<User> user = new AtomicStampedReference<>(new User("tom", 11), 0);
        List<Thread> ts = Lists.newArrayList();
        IntStream.rangeClosed(0, 10).forEach(i -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    user.getReference();
                }
            };
            ts.add(t);
        });
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
