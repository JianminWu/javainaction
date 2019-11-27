package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.JmxAttributeGauge;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.concurrent.TimeUnit;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * @Author: wujianmin
 * @Date: 2019/11/12 15:28
 * @Function:
 * @Version 1.0
 */
public class JMXGaugeExample {
    private static final MetricRegistry registry = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry).convertDurationsTo(TimeUnit.SECONDS).convertRatesTo(TimeUnit.SECONDS).build();

    public static void main(String[] args) throws InterruptedException, MalformedObjectNameException {
        reporter.start(10,TimeUnit.SECONDS);

        registry.register(name(JMXGaugeExample.class, "HeapMemoryUsage"),
                new JmxAttributeGauge(new ObjectName("java.lang:type=Memory"),"HeapMemoryUsage"));
        Thread.currentThread().join();
    }


    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
