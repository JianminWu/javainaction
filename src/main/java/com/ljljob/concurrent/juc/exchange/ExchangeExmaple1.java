package com.ljljob.concurrent.juc.exchange;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/16 16:00
 * @Function:
 * @Version 1.0
 */
public class ExchangeExmaple1 {

    public static void main(String[] args) throws InterruptedException {
        final Exchanger exchanger = new Exchanger();
        new Thread("A") {
            @Override
            public void run() {
                String ret = "I come from " + Thread.currentThread().getName();
                try {
                    Object v = exchanger.exchange(ret);
                    System.out.println(v);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        TimeUnit.SECONDS.sleep(1);
        new Thread("B") {
            @Override
            public void run() {
                String ret = "I come from " + Thread.currentThread().getName();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Object v = exchanger.exchange(ret);
                    System.out.println(v);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}

