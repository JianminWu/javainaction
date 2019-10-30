package com.ljljob.concurrent.struct;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/28 11:24
 * @Function:
 * @Version 1.0
 *
 * unbounded
 * 核心在于DelayQueue里面的元素要求实现{@link Delayed}接口
 * delayed 接口实现了{@link Comparable}
 * 实现里面的getDelay 如果getDelay返回<=0 表示超时 大于0表示未超时
 *
 */
public class DelayQueueExample {

    public static <E extends Delayed> DelayQueue<E> create() {
        return new DelayQueue<E>();
    }

    static class MyDelayEle implements Delayed {

        private final String data;

        private final long delay;

        public String getData() {
            return data;
        }

        public MyDelayEle(String data, long delay) {
            this.data = data;
            this.delay = System.currentTimeMillis() + delay;
        }

        public MyDelayEle(String data, long delay, TimeUnit unit) {
            this.data = data;
            this.delay = System.currentTimeMillis() + unit.toMillis(delay);
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return delay - System.currentTimeMillis();
        }

        @Override
        public int compareTo(Delayed o) {
            MyDelayEle that = (MyDelayEle) o;
            long diff = this.delay - that.delay;
            if (diff <= 0) {// 改成>=会造成问题
                return -1;
            } else {
                return 1;
            }
        }
    }
}
