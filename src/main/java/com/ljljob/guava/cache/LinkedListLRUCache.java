package com.ljljob.guava.cache;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 15:17
 * @Function:
 * @Version 1.0
 */
public class LinkedListLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final LinkedList<K> keyList;

    private final Map<K, V> valueMap;

    public LinkedListLRUCache(int limit) {
        this.limit = limit;
        this.keyList = new LinkedList<>();
        this.valueMap = new HashMap<>();
    }

    @Override
    public void put(K k, V v) {
        if (keyList.size() >= limit) {
            // remove oldest
            K oldestKey = keyList.removeFirst();
            valueMap.remove(oldestKey);
        }
//        System.out.println("put " + v + " in cache");
        keyList.addLast(k);
        valueMap.put(k, v);
    }

    @Override
    public V get(K k) {
        if (!keyList.contains(k)) {
            return null;
        } else {
            keyList.remove(k);
            keyList.addLast(k);
            return valueMap.get(k);
        }
    }

    @Override
    public V remove(K k) {
        keyList.remove(k);
        return valueMap.remove(k);
    }

    @Override
    public void clear() {
        keyList.clear();
        valueMap.clear();
    }

    @Override
    public int size() {
        return keyList.size();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        keyList.forEach(k -> {
            builder.append(k.toString()).append("=").append(valueMap.get(k)).append(",");
        });
        builder.replace(builder.length() - 1, builder.length(), "]");
        return builder.toString();
    }
}
