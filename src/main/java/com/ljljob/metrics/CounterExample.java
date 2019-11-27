package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/13 11:03
 * @Function:
 * @Version 1.0
 */
public class CounterExample {

    private final static MetricRegistry registry = new MetricRegistry();

    private final static ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).convertDurationsTo(TimeUnit.SECONDS).convertRatesTo(TimeUnit.SECONDS).build();

    public static void main(String[] args) {
        reporter.start(10,TimeUnit.SECONDS);

        Counter counter = registry.counter("counter");

        while (true){
            counter.inc();
            sleep(ThreadLocalRandom.current().nextInt(2));
        }
    }

    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
