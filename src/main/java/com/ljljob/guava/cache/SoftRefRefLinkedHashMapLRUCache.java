package com.ljljob.guava.cache;


import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 14:49
 * @Function:
 * @Version 1.0
 */
public class SoftRefRefLinkedHashMapLRUCache<K, V> implements LRUCache<K, V> {

    private final int limit;

    private final InnerLinkedHashMap<K, V> container;

    public SoftRefRefLinkedHashMapLRUCache(int limit) {
        this.limit = limit;
        container = new InnerLinkedHashMap(limit);
    }


    private static class InnerLinkedHashMap<K,V> extends LinkedHashMap<K, SoftReference<V>> {
        private final int limit;

        public InnerLinkedHashMap(int limit) {
            super(16, 0.75f, true);
            this.limit = limit;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return size() > limit;
        }
    }

    @Override
    public void put(K k, V v) {
        container.put(k, new SoftReference<V>(v));
    }

    @Override
    public V get(K k) {
        return container.get(k).get();
    }

    @Override
    public V remove(K k) {
        return container.remove(k).get();
    }

    @Override
    public void clear() {
        container.clear();
    }

    @Override
    public int size() {
        return container.size();
    }

    @Override
    public int limit() {
        return limit;
    }

    @Override
    public String toString() {
        return container.toString();
    }
}
