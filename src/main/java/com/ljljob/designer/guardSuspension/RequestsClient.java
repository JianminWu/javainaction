package com.ljljob.designer.guardSuspension;

import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 10:54
 * @Function:
 * @Version 1.0
 */
public class RequestsClient extends Thread{

    private final RequestsQueue queue;
    private final String value;
    private final Random random;

    public RequestsClient(RequestsQueue queue, String value) {
        this.queue = queue;
        this.value = value;
        this.random = new Random();
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("ClientAndService -> " + value);
            Request request = new Request(value);
            queue.pushRequests(request);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
