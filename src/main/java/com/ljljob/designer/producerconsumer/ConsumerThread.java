package com.ljljob.designer.producerconsumer;

import java.util.Objects;
import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:05
 * @Function:
 * @Version 1.0
 */
public class ConsumerThread extends Thread {

    private final MessageQueue messageQueue;

    private final Random random;

    public ConsumerThread(MessageQueue messageQueue) {
        this.random = new Random();
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        while (true) {
            Message message = messageQueue.popMessage();
            if (Objects.nonNull(message)) {
                System.out.println("consumer -> " + Thread.currentThread().getName() + " " + message.getMsg());
            }
        }
    }
}
