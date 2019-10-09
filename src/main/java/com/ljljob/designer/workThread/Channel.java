package com.ljljob.designer.workThread;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 10:18
 * @Function:
 * @Version 1.0
 */
public class Channel {

    private final LinkedList<Request> requestsQueue;

    private WorkThread[] workThreads;

    private int workerCount;

//    private final static int QUEUE_MAX_COUNT = 100;

    public Channel(int workerCount) {
        this.workerCount = workerCount;
        this.requestsQueue = new LinkedList<>();
        this.workThreads = new WorkThread[workerCount];
        init();
    }

    private void init() {
        IntStream.rangeClosed(0, this.workerCount - 1).forEach(i -> {
            WorkThread workThread = new WorkThread("Worker-Thread-" + i, this);
            workThreads[i] = workThread;
        });
    }

    public void start() {
        Arrays.asList(workThreads).forEach(WorkThread::start);
    }

    public void put(Request request) throws InterruptedException {
        synchronized (requestsQueue) {
            while (requestsQueue.size() >= workerCount) {
                requestsQueue.wait();
            }
            requestsQueue.addLast(request);
            requestsQueue.notifyAll();
        }
    }

    public Request take() throws InterruptedException {
        synchronized (requestsQueue) {
            while (requestsQueue.isEmpty()) {
                requestsQueue.wait();
            }
            Request request = requestsQueue.removeFirst();
            requestsQueue.notifyAll();
            return request;
        }
    }

}
