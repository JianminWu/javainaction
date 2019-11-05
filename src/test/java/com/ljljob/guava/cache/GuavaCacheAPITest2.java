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
public class GuavaCacheAPITest2 {

    static AtomicBoolean loadFromCache = new AtomicBoolean(true);

    /**
     * weakReference 每次GC调用都会回收
     *
     * @throws InterruptedException
     */
    @Test
    public void testWeakReference() throws InterruptedException {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .weakKeys()
                .weakValues()
//                .expireAfterWrite(2, TimeUnit.SECONDS)
                .build(getStringEmployeeCacheLoader());
        cache.getUnchecked("AAA");
        cache.getUnchecked("ABA");
        cache.getUnchecked("ACA");
        cache.getUnchecked("ADA");

        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        assertThat(cache.getIfPresent("AAA"), nullValue());
    }

    @Test
    public void testSoftReference() throws Exception {
        LoadingCache<String, Employee> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2,TimeUnit.SECONDS)
                .softValues()
                .build(getStringEmployeeCacheLoader());
        int i = 0;
        for (; ; ) {
            cache.put("Alex" + i, Employee.builder().addr(i + "").name(i + "").build());
            System.out.println("The Employee [" + (i++) + "] is store into cache.");
            TimeUnit.MILLISECONDS.sleep(600);
        }
    }

    private CacheLoader<String, Employee> getStringEmployeeCacheLoader() {
        return new CacheLoader<String, Employee>() {
            @Override
            public Employee load(String key) throws Exception {
//                    System.out.println("++++++++");
                return GuavaCacheAPITest1.EmployeeDao.findByName(key);
            }
        };
    }

    public static class EmployeeDao {

        public static Employee findByName(String name) {
//            System.out.println("*****");

            loadFromCache.set(false);
            return Employee.builder().name(name).addr(name).build();
        }
    }

    private static void assertLoadFromCache() {
        assertThat(true, equalTo(loadFromCache.get()));
    }

    private static void assertLoadFromDbThenReset() {
        assertThat(false, equalTo(loadFromCache.get()));
        loadFromCache.set(true);
    }


    static class TestObj {
        private final int index;

        public TestObj(int index) {
            this.index = index;
        }

        private byte[] bytes = new byte[1024 * 1024];

        @Override
        public String toString() {
            return "obj-" + index;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("obj-" + index + "===============has be gc");
        }
    }
}