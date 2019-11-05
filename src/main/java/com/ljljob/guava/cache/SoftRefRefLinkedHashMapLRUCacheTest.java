package com.ljljob.guava.cache;


import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 14:49
 * @Function:
 * @Version 1.0
 */
public class SoftRefRefLinkedHashMapLRUCacheTest {
    public static void main(String[] args) throws InterruptedException {
        final SoftRefRefLinkedHashMapLRUCache<String, byte[]> cache = new SoftRefRefLinkedHashMapLRUCache<>(100);

        for (int i = 0; i < 1000; i++)
        {
            cache.put(String.valueOf(i), new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(600);
            System.out.println("The " + i + " entity is cached.");
        }
    }
}


