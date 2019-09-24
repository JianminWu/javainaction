package com.ljljob.thread.threadException;


/**
 * @Author: wujianmin
 * @Date: 2019/9/20 10:11
 * @Function:
 * @Version 1.0
 */
public class ThreadExceptionHandler {

    private static final int A = 1;

    private static final int B = 0;

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                int C = A / B;
            }
        };
        t.setUncaughtExceptionHandler((thread, exception) -> {
            System.out.println("exception thread -> " + thread.getName());
            System.out.println("exception is -> " + exception.getMessage());

        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
