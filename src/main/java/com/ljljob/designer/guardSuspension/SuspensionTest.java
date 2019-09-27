package com.ljljob.designer.guardSuspension;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 11:05
 * @Function:
 * @Version 1.0
 */
public class SuspensionTest {

    public static void main(String[] args) {
        RequestsQueue queue = new RequestsQueue();
        IntStream.rangeClosed(0,4).forEach(i->{
            new RequestsClient(queue,Thread.currentThread().getName()).start();
        });
        RequestServer server = new RequestServer(queue);
        server.start();

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        server.close();
    }
}
