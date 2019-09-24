package com.ljljob.thread.limitThreadPoll;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/19 17:39
 * @Function:
 * @Version 1.0
 */
public class LimitThreadPollDemo {

    private static LinkedList<Control> CONTROLLER = Lists.newLinkedList();
    private static int MAX_THREAD_SIZE = 5;

    public static void main(String[] args) {
        List<Thread> threads = Lists.newArrayList();

        Stream.of("T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8", "T9", "T10", "T11", "T12")
                .map(LimitThreadPollDemo::createWorkThread)
                .forEach(t -> {
                    t.start();
                    threads.add(t);
                });
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static Thread createWorkThread(String name) {
        return new Thread(() -> {
            synchronized (CONTROLLER) {
                while (CONTROLLER.size() >= MAX_THREAD_SIZE) {
                    // 大于
                    try {
                        CONTROLLER.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                CONTROLLER.addLast(new Control());
            }
            Optional.of("The machine [" + Thread.currentThread().getName() + "] BEGIN to work")
                    .ifPresent(System.out::println);
            Optional.of("The machine [" + Thread.currentThread().getName() + "] is working")
                    .ifPresent(System.out::println);

            // 模拟工作
            try {
                Thread.sleep(10_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 工作完成
            synchronized (CONTROLLER) {
                CONTROLLER.removeFirst();
                // 唤醒其他正在等待的机器
                CONTROLLER.notifyAll();
                Optional.of("The machine [" + Thread.currentThread().getName() + "] END work")
                        .ifPresent(System.out::println);
            }


        }, name);
    }

    public static class Control {

    }
}
