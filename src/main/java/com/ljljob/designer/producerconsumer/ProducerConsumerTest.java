package com.ljljob.designer.producerconsumer;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:07
 * @Function:
 * @Version 1.0
 */
public class ProducerConsumerTest {

    public static void main(String[] args) {
        final MessageQueue messageQueue = new MessageQueue(120);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            new ProducerThread(messageQueue).start();
        });

        IntStream.rangeClosed(1, 2).forEach(i -> {
            new ConsumerThread(messageQueue).start();
        });
    }
}
