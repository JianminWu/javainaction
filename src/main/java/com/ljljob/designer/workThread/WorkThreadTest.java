package com.ljljob.designer.workThread;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 10:50
 * @Function:
 * @Version 1.0
 */
public class WorkThreadTest {

    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.start();

        new TransportThread("Jim", channel).start();
        new TransportThread("Mike", channel).start();
        new TransportThread("Lily", channel).start();
    }
}
