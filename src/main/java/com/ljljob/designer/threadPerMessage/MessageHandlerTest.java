package com.ljljob.designer.threadPerMessage;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:55
 * @Function:
 * @Version 1.0
 */
public class MessageHandlerTest {

    public static void main(String[] args) {
        MessageHandler messageHandler = new MessageHandler();

        IntStream.rangeClosed(1, 4).forEach(i -> {
            messageHandler.handler(new Message(String.valueOf(i)));
        });

        messageHandler.shutdown();
    }
}
