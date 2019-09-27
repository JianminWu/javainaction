package com.ljljob.designer.guardSuspension;

import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 10:50
 * @Function:
 * @Version 1.0
 */
public class RequestServer extends Thread {

    private final RequestsQueue requestsQueue;

    private final Random random;

    private volatile boolean close = false;

    public RequestServer(RequestsQueue requestsQueue) {
        this.requestsQueue = requestsQueue;
        this.random = new Random();
    }


    @Override
    public void run() {
        while (!close) {
            try {
                Request request = requestsQueue.popRequest();
                if (null == request) {
                    continue;
                }
                System.out.println("Server -> " + request.getValue());
//                Thread.sleep(random.nextInt(1));
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void close() {
        this.close = true;
        System.out.println(this);
        this.interrupt();
    }
}
