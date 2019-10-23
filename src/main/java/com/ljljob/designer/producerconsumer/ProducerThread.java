package com.ljljob.designer.producerconsumer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 11:50
 * @Function:
 * @Version 1.0
 */
public class ProducerThread extends Thread {

    private final MessageQueue messageQueue;

    private final AtomicInteger index = new AtomicInteger(0);

    private final Random random;

    public ProducerThread(MessageQueue messageQueue) {
        this.random = new Random();
        this.messageQueue = messageQueue;
    }


    @Override
    public void run() {
        while (true) {
            Message message = new Message("index -> " + Thread.currentThread().getName() + " " + index.incrementAndGet());
            System.out.println("produce -> " + Thread.currentThread().getName() + " " + message.getMsg());
            messageQueue.pushMessage(message);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
