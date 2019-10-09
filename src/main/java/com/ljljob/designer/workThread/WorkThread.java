package com.ljljob.designer.workThread;

import java.util.Random;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 10:19
 * @Function:
 * @Version 1.0
 */
public class WorkThread extends Thread {
    private String name;

    private static final Random random = new Random(System.currentTimeMillis());

    private final Channel channel;

    public WorkThread(String name, Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = channel.take();
                System.out.println(getName() + " take the msg -> " + request.getVal());
                try {
                    Thread.sleep(random.nextInt(1_000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
