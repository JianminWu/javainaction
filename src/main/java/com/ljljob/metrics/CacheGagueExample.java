package com.ljljob.metrics;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/12 10:49
 * @Function:
 * @Version 1.0
 */
public class CacheGagueExample {

    private static final MetricRegistry registry = new MetricRegistry();

    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();

    public static void main(String[] args) throws InterruptedException {
        reporter.start(10, TimeUnit.SECONDS);
        registry.register("cache_gague", new CachedGauge<Long>(10, TimeUnit.SECONDS) {
            @Override
            protected Long loadValue() {
                return business();
            }
        });
        Thread.currentThread().join();
    }

    private static long business() {
        System.out.println("================");
        return System.currentTimeMillis();
    }


    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
