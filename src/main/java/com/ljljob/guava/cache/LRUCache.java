package com.ljljob.guava.cache;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 14:21
 * @Function:
 * @Version 1.0
 * LRU Least Recently Used 最近最少使用算法
 */
public interface LRUCache<K,V> {


    void put(K k,V v);

    V get(K k);

    V remove(K k);

    void clear();

    int size();

    int limit();
}
