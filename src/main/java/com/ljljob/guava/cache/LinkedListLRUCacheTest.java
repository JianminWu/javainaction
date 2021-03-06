package com.ljljob.guava.cache;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 14:57
 * @Function:
 * @Version 1.0
 */
public class LinkedListLRUCacheTest {

    public static void main(String[] args) {
        LinkedListLRUCache<String, String> cache = new LinkedListLRUCache<String, String>(3);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        System.out.println(cache);
        cache.put("4","4");
        System.out.println(cache);
        cache.get("2");
        cache.get("2");
        System.out.println();
        System.out.println(cache);
    }
}
