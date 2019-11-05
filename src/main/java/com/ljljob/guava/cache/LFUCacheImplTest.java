package com.ljljob.guava.cache;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 16:46
 * @Function:
 * @Version 1.0
 */
public class LFUCacheImplTest {
    public static void main(String[] args) {
        LFUCacheImpl<String, String> cache = new LFUCacheImpl<>(3);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.get("2");
        cache.get("1");
        cache.get("3");
        System.out.println(cache);
        cache.put("4","4");
        System.out.println(cache);
    }
}
