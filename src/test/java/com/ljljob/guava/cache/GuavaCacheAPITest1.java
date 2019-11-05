package com.ljljob.guava.cache;

import com.google.common.cache.*;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/11/1 15:37
 * @Function:
 * @Version 1.0
 *
 * basic usage for Guava cache
 * Four  Eviction Strategy (Size Weight AccessTime WriteTime)
 */
public class GuavaCacheAPITest1 {

    static AtomicBoolean loadFromCache = new AtomicBoolean(true);

    @Test
    public void testCacheBuilder() {
        CacheLoader<String, Employee> cacheLoader = getStringEmployeeCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().build(cacheLoader);
        assertThat(cache.getUnchecked("ALEX"), CoreMatchers.notNullValue());
        assertLoadFromDbThenReset();
        cache.getUnchecked("ALEX");
        assertLoadFromCache();
        cache.getUnchecked("ALEX");
        assertLoadFromCache();

        assertThat(cache.getUnchecked("JIM"), CoreMatchers.notNullValue());
        assertLoadFromDbThenReset();
        cache.getUnchecked("JIM");
        assertLoadFromCache();
    }

    private CacheLoader<String, Employee> getStringEmployeeCacheLoader() {
        return new CacheLoader<String, Employee>() {
                @Override
                public Employee load(String key) throws Exception {
//                    System.out.println("++++++++");
                    return EmployeeDao.findByName(key);
                }
            };
    }

    /**
     * 逐出策略按固定size逐出LRU算法
     */
    @Test
    public void testEvictionBySize(){
        CacheLoader<String, Employee> cacheLoader = getStringEmployeeCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumSize(2).build(cacheLoader);
        assertThat(cache.getUnchecked("ALEX"),notNullValue());
        assertLoadFromDbThenReset();
        assertThat(cache.getUnchecked("JIM"),notNullValue());
        assertLoadFromDbThenReset();
        assertThat(cache.getUnchecked("JACK"),notNullValue());
        assertLoadFromDbThenReset();
        assertThat(cache.getUnchecked("JACK"),notNullValue());
        assertLoadFromCache();
        cache.getUnchecked("ALEX");
        assertThat(cache.getIfPresent("JIM"),nullValue());
    }

    /**
     * 根据weight逐出 需要定义一个weighter称重计
     */
    @Test
    public void testEvictionByWeight(){
        CacheLoader<String, Employee> cacheLoader = getStringEmployeeCacheLoader();
        Weigher<String, Employee> weigher=(k,v)-> v.getName().length()+v.getAddr().length();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().maximumWeight(8).weigher(weigher).build(cacheLoader);

        cache.getUnchecked("AA");
        cache.getUnchecked("BB");
        cache.getUnchecked("CC");

        assertThat(cache.getIfPresent("AA"),nullValue());
    }

    /**
     * read will refresh the expireTime
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByAccessTime() throws InterruptedException {
        CacheLoader<String, Employee> cacheLoader = getStringEmployeeCacheLoader();
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterAccess(2, TimeUnit.SECONDS).<String, Employee>build(cacheLoader);
        assertThat(cache.getUnchecked("a"),notNullValue());
        TimeUnit.SECONDS.sleep(2);
        assertThat(cache.getIfPresent("a"),nullValue());
    }

    /**
     * Write time => write/update
     * only write or update can refresh  the expireTime
     * @throws InterruptedException
     */
    @Test
    public void testEvictionByWriteTime() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS).build(getStringEmployeeCacheLoader());
        assertThat(cache.getUnchecked("aa"),notNullValue());
        TimeUnit.SECONDS.sleep(3);
        assertThat(cache.getIfPresent("aa"),nullValue());
    }

    public static class EmployeeDao {

        public static Employee findByName(String name) {
//            System.out.println("*****");

            loadFromCache.set(false);
            return Employee.builder().name(name).addr(name).build();
        }
    }

    private static void assertLoadFromCache(){
        assertThat(true,equalTo(loadFromCache.get()));
    }

    private static void assertLoadFromDbThenReset(){
        assertThat(false,equalTo(loadFromCache.get()));
        loadFromCache.set(true);
    }

}