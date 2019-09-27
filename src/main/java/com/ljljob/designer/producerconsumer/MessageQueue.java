package com.ljljob.designer.producerconsumer;

import org.omg.PortableServer.POA;

import java.util.LinkedList;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 11:50
 * @Function:
 * @Version 1.0
 */
public class MessageQueue {

    private final LinkedList<Message> messageQueue = new LinkedList<Message>();

    private final int MAX_DEFAULT_QUEUE_SIZE = 100;

    private int maxQueueSize;

    public MessageQueue(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public void pushMessage(Message message) {
        synchronized (messageQueue) {
            while (messageQueue.size() >= maxQueueSize) {
                try {
                    messageQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            messageQueue.addLast(message);
            messageQueue.notifyAll();
        }
    }

    public Message popMessage() {
        synchronized (messageQueue) {
            while (messageQueue.size() <= 0) {
                try {
                    messageQueue.wait();
                } catch (InterruptedException e) {
                    break;
                }
            }
            messageQueue.notifyAll();
            return messageQueue.removeFirst();
        }
    }

}
