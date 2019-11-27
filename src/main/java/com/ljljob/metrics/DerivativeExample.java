package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.DerivativeGauge;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.sql.Time;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/12 15:46
 * @Function:
 * @Version 1.0
 */
public class DerivativeExample {

    private static final MetricRegistry registry = new MetricRegistry();

    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();


    public static void main(String[] args) throws InterruptedException {
        reporter.start(10, TimeUnit.SECONDS);

        CacheLoader<String, String> cacheLoader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) throws Exception {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).recordStats().build(cacheLoader);
        cache.stats();
        Gauge<CacheStats> cacheStatsGauge = cache::stats;
        registry.register(MetricRegistry.name(DerivativeExample.class, "Derivative_hit_gauge"), new DerivativeGauge<CacheStats, Long>(cacheStatsGauge) {
            @Override
            protected Long transform(CacheStats stats) {
                return stats.hitCount();
            }
        });
        registry.register(MetricRegistry.name(DerivativeExample.class, "Derivative_miss_gauge"), new DerivativeGauge<CacheStats, Long>(cacheStatsGauge) {
            @Override
            protected Long transform(CacheStats stats) {
                return stats.missCount();
            }
        });
        while (true) {
            cache.getUnchecked(String.valueOf(ThreadLocalRandom.current().nextInt(20)));
            sleep(TimeUnit.MILLISECONDS,ThreadLocalRandom.current().nextInt(500));
        }

    }
    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void sleep(TimeUnit unit,Integer time) {
        try {
            unit.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
