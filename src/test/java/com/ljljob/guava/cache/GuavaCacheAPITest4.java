package com.ljljob.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @Author: wujianmin
 * @Date: 2019/11/1 15:37
 * @Function:
 * @Version 1.0
 * <p>
 * Weak Or Soft Reference
 */
public class GuavaCacheAPITest4 {

    /**
     * cache中的stat是不可变final修饰的类,且没有set方法
     * 每次stat需要重新调用才是最新的
     * 这样实现了无锁的线程安全
     */
    @Test
    public void testStat(){
        CacheLoader<String,String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().recordStats().build(cacheLoader);
        cache.getUnchecked("alex");
        assertThat(cache.stats().hitCount(),equalTo(0L));
        assertThat(cache.stats().missCount(),equalTo(1L));

        cache.getUnchecked("alex");
        assertThat(cache.stats().hitCount(),equalTo(1L));
        assertThat(cache.stats().missCount(),equalTo(1L));
    }

    /**
     * 通过spec字符串的形式定义cache configuration
     */
    @Test
    public void testSpec(){
        String cacheSpec = "maximumSize=2,recordStats";
        CacheLoader<String, String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.from(cacheSpec).build(cacheLoader);
        assertThat(cache.getUnchecked("alex"),equalTo("ALEX"));
        assertThat(cache.getUnchecked("jim"),equalTo("JIM"));

        assertThat(cache.getUnchecked("cc"),equalTo("CC"));

        assertThat(cache.getIfPresent("alex"),nullValue());
    }
}