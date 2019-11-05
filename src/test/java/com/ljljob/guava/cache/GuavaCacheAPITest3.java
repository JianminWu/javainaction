package com.ljljob.guava.cache;

import com.google.common.cache.*;
import com.google.common.collect.Maps;
import org.checkerframework.checker.units.qual.C;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.fail;

/**
 * @Author: wujianmin
 * @Date: 2019/11/1 17:51
 * @Function:
 * @Version 1.0
 */
public class GuavaCacheAPITest3 {


    @Test
    public void testLoadNullValue(){
        CacheLoader<String, Employee> cacheLoader = CacheLoader.<String, Employee>from(k -> k.equals("null") ? null : new Employee(k, k));
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().build(cacheLoader);

        try {
            assertThat(cache.getUnchecked("null"),nullValue());
            fail(" can not process here ");
        } catch (Exception e) {
            assertThat((e instanceof CacheLoader.InvalidCacheLoadException),equalTo(true));
        }

    }

    @Test
    public void testCacheRefresh() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        CacheLoader<String,Long> cacheLoader = CacheLoader.from(k->{
            counter.getAndIncrement();
            return System.currentTimeMillis();
        });
        LoadingCache<String, Long> cache = CacheBuilder.newBuilder()
                .refreshAfterWrite(2, TimeUnit.SECONDS)
                .build(cacheLoader);
        Long original = cache.getUnchecked("Alex");
        assertThat(original,notNullValue());
        assertThat(counter.get(),equalTo(1));
        TimeUnit.SECONDS.sleep(3);
        Long older = cache.getUnchecked("Alex");
        assertThat(older,notNullValue());
        assertThat(counter.get(),equalTo(2));
        assertThat(Objects.equals(original, older),equalTo(false));

    }

    @Test
    public void testPreLoad(){
        CacheLoader<String,String> cacheLoader = CacheLoader.from(String::toUpperCase);
        LoadingCache<String, String> cache = CacheBuilder.newBuilder().build(cacheLoader);
        Map< String,  String> preLoad = Maps.newHashMap();
        preLoad.put("k1","v1");
        preLoad.put("k2","v2");
        preLoad.put("k3","v3");
        preLoad.put("k4","v4");
        cache.putAll(preLoad);
        assertThat(cache.getUnchecked("k1"),equalTo("v1"));
        assertThat(cache.size(),equalTo(4L));
        assertThat(cache.getUnchecked("alex"),equalTo("ALEX"));
    }


    @Test
    public void testRemoveWithNotification(){
        CacheLoader<String,String> cacheLoader = CacheLoader.from(String::toUpperCase);
        RemovalListener<String, String> listener=(notification)->{
            RemovalCause cause = notification.getCause();
//            System.out.println(cause);
            assertThat(cause,is(RemovalCause.SIZE));
            String removeKey = notification.getKey();
            String removeValue = notification.getValue();
            assertThat(removeKey,equalTo("k1"));
            assertThat(removeValue,equalTo("K1"));
        };
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .removalListener(listener)
                .build(cacheLoader);
        cache.getUnchecked("k1");
        cache.getUnchecked("k2");
        cache.getUnchecked("k3");
    }

}
