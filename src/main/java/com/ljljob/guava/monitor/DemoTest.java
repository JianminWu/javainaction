package com.ljljob.guava.monitor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/30 10:29
 * @Function:
 * @Version 1.0
 */
public class DemoTest {

    static AtomicInteger i = new AtomicInteger(0);

    public static void main(String[] args) {
        IntStream.rangeClosed(1,5).boxed().forEach(j->{
            new Thread(()->{
                while (i.get()<100){
                    System.out.println(Thread.currentThread().getName()+"--->"+i.getAndIncrement());
                }
            }).start();
        });
    }
}
