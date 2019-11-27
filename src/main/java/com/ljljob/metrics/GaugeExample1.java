package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/8 17:06
 * @Function:
 * @Version 1.0
 */
public class GaugeExample1 {

    private final static MetricRegistry REGISTRY = new MetricRegistry();

    private final static ConsoleReporter REPORTER = ConsoleReporter.forRegistry(REGISTRY).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();

    private final static BlockingQueue<Long> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        REGISTRY.register("queue", (Gauge<Integer>) queue::size);
        REPORTER.start(10, TimeUnit.SECONDS);
        new Thread(() -> {
            while (true) {
                queue.add(System.currentTimeMillis());
                sleep(ThreadLocalRandom.current().nextInt(1,2));
            }
        }).start();
        new Thread(() -> {
            while (true) {
                queue.poll();
                sleep(ThreadLocalRandom.current().nextInt(1,5));
            }
        }).start();
    }

    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
