package com.ljljob.designer.threadPerMessage;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:53
 * @Function:
 * @Version 1.0
 */
public class MessageHandler {

    private final ExecutorService executorService;

    private final Random random;

    public MessageHandler() {
        this.random = new Random();
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public void handler(Message message) {
        executorService.submit(() -> {
            String val = message.getVal();
            System.out.println(Thread.currentThread().getName() + " get msg is + " + val);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdown() {
        executorService.shutdown();
    }

}
