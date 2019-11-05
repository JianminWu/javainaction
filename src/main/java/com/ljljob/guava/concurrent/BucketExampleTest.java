package com.ljljob.guava.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 10:15
 * @Function:
 * @Version 1.0
 */
public class BucketExampleTest {

    private static final AtomicInteger DATA_CREATOR = new AtomicInteger(0);

    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        BucketExample1<Integer> bucket = new BucketExample1<Integer>(queue);
        IntStream.range(0, 5).forEach(i ->
        {
            new Thread(() ->
            {
                for (; ; )
                {
                    int data = DATA_CREATOR.getAndIncrement();
                    bucket.put(data);
                    try
                    {
                        TimeUnit.MILLISECONDS.sleep(200L);
                    } catch (Exception e)
                    {
                        if (e instanceof IllegalStateException)
                        {
                            System.out.println(e.getMessage());
                        }
                    }
                }       //25

                //10

                //5:2
            }).start();
        });


        IntStream.range(0, 5)
                .forEach(i -> new Thread(() ->
                        {
                            for (; ; )
                            {
                                bucket.take(x -> System.out.println(currentThread() + " W " + x));
                            }
                        }).start()
                );
    }
}
