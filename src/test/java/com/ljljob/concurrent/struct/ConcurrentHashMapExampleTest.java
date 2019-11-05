package com.ljljob.concurrent.struct;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * @Author: wujianmin
 * @Date: 2019/10/30 15:01
 * @Function:
 * @Version 1.0
 */
public class ConcurrentHashMapExampleTest {

    private ConcurrentHashMap<String, Object> map = null;

    @Before
    public void setUp() throws Exception {
        map = new ConcurrentHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");
        map.put("k4", "v4");
    }

    @Test
    public void testCompute() {
        assertThat(map.computeIfPresent("k1", (k1, v1) -> {
            assertThat(k1, equalTo("k1"));
            assertThat(v1, equalTo("v1"));
            return v1 + "t1";
        }), equalTo("v1t1"));

    }

    @Test
    public void testMerge() {
        assertThat(map.merge("k1", "v2", (v1, v2) -> v1.toString() + v2.toString()), equalTo("v1v2"));
        assertThat(map.get("k1"), equalTo("v1v2"));
    }

    @Test
    public void testReduce() {
        assertThat(map.reduceKeys(2, (k1, k2) -> k1 + k2), equalTo("k1k2k3k4"));
        assertThat(map.reduceValues(2, (v1, v2) -> v1.toString() + v2.toString()), equalTo("v1v2v3v4"));
        Map.Entry<String, Object> reduceResult = map.reduceEntries(2, (e1, e2) -> new HashMap.Entry<String, Object>() {
            @Override
            public String getKey() {
                return e1.getKey() + e2.getKey();
            }

            @Override
            public Object getValue() {
                return e1.getValue().toString() + e2.getValue().toString();
            }

            @Override
            public Object setValue(Object value) {
                return null;
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }

            @Override
            public int hashCode() {
                return this.hashCode();
            }
        });
        assertThat(reduceResult.getValue(),equalTo("v1v2v3v4"));
        assertThat(reduceResult.getKey(),equalTo("k1k2k3k4"));
    }

    @Test
    public void testSearch(){

        assertThat(map.searchKeys(2,(k1)-> k1.equals("k1")?"v111":"v"),equalTo("v111"));
        assertThat(map.get("k1"),equalTo("v1"));
    }

    @Test
    public void testGerOrDefault(){
        assertThat(map.getOrDefault("k1","v111"),equalTo("v1"));
        assertThat(map.getOrDefault("k6","v111"),equalTo("v111"));
    }

    @Test
    public void testForeach(){
        AtomicInteger i = new AtomicInteger();
        map.forEach((k,v)->{
            int num = i.incrementAndGet();
            assertThat(k,equalTo("k"+ num));
            assertThat(v,equalTo("v"+ num));
        });
    }


    @After
    public void tearDown() throws Exception {
        map = null;
    }

}