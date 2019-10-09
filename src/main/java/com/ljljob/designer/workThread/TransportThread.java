package com.ljljob.designer.workThread;

import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 10:47
 * @Function:
 * @Version 1.0
 */
public class TransportThread extends Thread {

    private String name;

    private final Channel channel;

    private final Random random = new Random(System.currentTimeMillis());


    public TransportThread(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            String msg = String.valueOf(random.nextInt(100));
            Request request = new Request(msg);
            try {
                channel.put(request);
                Thread.sleep(random.nextInt(1_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
