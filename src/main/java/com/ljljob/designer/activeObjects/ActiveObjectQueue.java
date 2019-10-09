package com.ljljob.designer.activeObjects;

import java.util.LinkedList;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:25
 * @Function:
 * @Version 1.0
 */
public class ActiveObjectQueue {

    private final static int MAX_METHOD_REQUEST_SIZE = 100;

    private final int requestSize;

    private final LinkedList<MethodRequest> methodRequests = new LinkedList<>();

    public ActiveObjectQueue() {
        this(MAX_METHOD_REQUEST_SIZE);
    }

    public ActiveObjectQueue(int requestSize) {
        this.requestSize = requestSize;
    }

    public void put(MethodRequest methodRequest) {
        synchronized (methodRequests) {
            while (methodRequests.size() >= requestSize) {
                try {
                    methodRequests.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            methodRequests.notifyAll();
            methodRequests.addLast(methodRequest);
        }
    }

    public MethodRequest take() {
        synchronized (methodRequests) {
            while (methodRequests.isEmpty()) {
                try {
                    methodRequests.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            methodRequests.notifyAll();
            return methodRequests.removeFirst();
        }
    }
}
