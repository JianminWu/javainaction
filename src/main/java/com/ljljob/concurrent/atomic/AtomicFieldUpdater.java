package com.ljljob.concurrent.atomic;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/14 17:27
 * @Function:
 * @Version 1.0
 */
public class AtomicFieldUpdater {

    public static void main(String[] args) {
        stupidUpdater();

    }

    private static void stupidUpdater() {
        List<Thread> ts = Lists.newArrayList();
        StupidUser user = new StupidUser("tom", 0);
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (user.getAge()<1000000){
                        user.increamentAge();
                    }
                }
            };
            ts.add(t);
        });
        ts.forEach(t -> {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(user.getAge());
    }

    private void testSafeUpder() {
        List<Thread> ts = Lists.newArrayList();
        User user = new User("tom", 0);
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (user.getAge()<10000){
                        user.increamentAge();
                    }
                }
            };
            ts.add(t);
        });
        ts.forEach(t -> {
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(user.getAge());
    }

    static class StupidUser {
        private String name;

        private int age;

        public StupidUser(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void increamentAge() {
            age++;
        }

        public int getAge() {
            return age;
        }
    }

    static class User {
        private AtomicIntegerFieldUpdater updater = AtomicIntegerFieldUpdater.newUpdater(this.getClass(), "age");

        private String name;

        private volatile int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void increamentAge() {
            updater.getAndIncrement(this);
        }

        public int getAge() {
            return updater.get(this);
        }
    }
}
