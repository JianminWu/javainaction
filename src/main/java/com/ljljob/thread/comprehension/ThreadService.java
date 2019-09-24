package com.ljljob.thread.comprehension;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/9/16 15:04
 * @Function:
 * @Version 1.0
 */
public class ThreadService {

    private Thread threadService;

    private Boolean isDone = Boolean.FALSE;

    public void execute(Runnable task) {
        threadService = new Thread(() -> {
            Thread innerThread = new Thread(task);
            innerThread.setDaemon(true);
            innerThread.start();
            try {
                innerThread.join();
                isDone = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadService.start();
    }

    public void setTimeOut(Long mills) {
        Stopwatch stopwatch = Stopwatch.createStarted();

        while (!isDone) {
            if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > mills) {
                // 超时
                System.out.println("任务超时");
                threadService.interrupt();
                break;
            }
            try {
                threadService.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("线程中断");
                e.printStackTrace();
            }
        }
        // 执行完成重置状态
        isDone = Boolean.TRUE;
    }
}
