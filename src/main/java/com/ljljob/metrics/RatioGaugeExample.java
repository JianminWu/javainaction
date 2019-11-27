package com.ljljob.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/11/12 10:33
 * @Function:
 * @Version 1.0
 */
public class RatioGaugeExample {

    private static final MetricRegistry registry = new MetricRegistry();

    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertDurationsTo(TimeUnit.SECONDS)
            .convertRatesTo(TimeUnit.SECONDS).build();

    private static final Meter successMeter = new Meter();
    private static final Meter failMeter = new Meter();
    private static final Meter totalMeter = new Meter();


    public static void main(String[] args) {
        reporter.start(10, TimeUnit.SECONDS);
        registry.register("ratioGauge", new RatioGauge() {
            @Override
            protected Ratio getRatio() {
                System.out.println(successMeter.getCount());
                System.out.println(totalMeter.getCount());
                return Ratio.of(successMeter.getCount(), totalMeter.getCount());
            }
        });

        while (true){
            business();
        }

    }

    private static void business() {
        totalMeter.mark();
        try {
            int i = 1 / ThreadLocalRandom.current().nextInt(3);
            successMeter.mark();
        } catch (Exception e) {
            e.printStackTrace();
            failMeter.mark();
        }
        sleep(ThreadLocalRandom.current().nextInt(3));
    }


    private static void sleep(Integer time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
