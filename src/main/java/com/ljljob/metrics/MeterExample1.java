package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/8 16:55
 * @Function:
 * @Version 1.0
 *
 * Meter
 */
public class MeterExample1 {

    private final static MetricRegistry registry = new MetricRegistry();

    private final static Meter meter = registry.meter("tps");

    private final static ConsoleReporter reporter =
            ConsoleReporter.forRegistry(registry)
                    .convertDurationsTo(TimeUnit.MINUTES)
                    .convertRatesTo(TimeUnit.MINUTES)
                    .build();

    public static void main(String[] args) {
        reporter.start(10,TimeUnit.SECONDS);
        for (; ; ) {
            meter.mark();
            sleep();
        }
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
