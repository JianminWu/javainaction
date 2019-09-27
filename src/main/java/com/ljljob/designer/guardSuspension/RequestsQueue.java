package com.ljljob.designer.guardSuspension;

import java.util.LinkedList;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 10:45
 * @Function:
 * @Version 1.0
 */
public class RequestsQueue {

    private LinkedList<Request> requestsQueue = new LinkedList<>();

    public Request popRequest()  {
        synchronized (requestsQueue) {
            while (requestsQueue.size() <= 0) {
                try {
                    requestsQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return requestsQueue.removeFirst();
        }
    }

    public void pushRequests(Request request) {
        synchronized (requestsQueue) {
            requestsQueue.addLast(request);
            requestsQueue.notifyAll();
        }

    }
}
