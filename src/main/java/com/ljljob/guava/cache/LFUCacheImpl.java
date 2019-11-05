package com.ljljob.guava.cache;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author: wujianmin
 * @Date: 2019/10/31 16:29
 * @Function:
 * @Version 1.0
 */
public class LFUCacheImpl<K, V> implements LFUCache<K, V> {

    private HashMap<K, CacheObj<V>> map;

    private final int limit;

    public LFUCacheImpl(int limit) {
        this.limit = limit;
        map = new HashMap<>();
    }

    private static class CacheObj<V> {
        /**
         * 访问次数
         */
        protected long accessCount = 0;

        private final V v;

        public CacheObj(V v) {
            this.v = v;
        }

        public V get() {
            this.accessCount++;
            return v;
        }

        @Override
        public String toString() {
            return v.toString();
        }
    }


    @Override
    public void put(K k, V v) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        CacheObj<V> co = new CacheObj<>(v);
        if(map.size()>=limit){
            // remove least used
            pruneCache();
        }
        map.put(k,co);
    }

    protected int pruneCache(){
        int count = 0;
        if(map.size()==0){
            return 0;
        }else{
            Iterator<CacheObj<V>> values = map.values().iterator();
            Long leastUse = null;

            while (values.hasNext()){
                CacheObj<V> co = values.next();
                if(leastUse==null ||leastUse>co.accessCount){
                    leastUse = co.accessCount;
                }
            }
            values = map.values().iterator();
            while (values.hasNext()){
                CacheObj<V> co = values.next();
                if(co.accessCount<=leastUse){
                    values.remove();
                    count++;
                }

            }
        }
        return count;
    }

    @Override
    public V get(K k) {
        Preconditions.checkNotNull(k);
        if(map.containsKey(k)){
            CacheObj<V> co = map.get(k);
            return co.get();
        }
        return null;
    }

    @Override
    public V remove(K k) {
        CacheObj<V> remove = map.remove(k);
        V v = remove.get();
        return v;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        map.forEach((k,v)->{
            builder.append(k).append("=").append(v).append(",");
        });
        builder.replace(builder.length()-1,builder.length(),"]");
        return builder.toString();
    }
}
