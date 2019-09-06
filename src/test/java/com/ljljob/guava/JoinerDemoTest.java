package com.ljljob.guava;


import com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @Author: wujianmin
 * @Date: 2019/9/2 10:29
 * @Function:
 * @Version 1.0
 */
public class JoinerDemoTest {

    @Test
    public void testJoiner() {
        List<String> list = Arrays.asList("Hello", "World", "Test");
        String join = Joiner.on(",").join(list);
        assertThat(join, equalTo("Hello,World,Test"));
    }

    @Test
    public void testJoinerWithNull() {
        List<String> list = Arrays.asList("Hello", "World", "Test", null);
        String join = Joiner.on(",").skipNulls().join(list);
        assertThat(join, equalTo("Hello,World,Test"));
    }

    @Test
    public void testJoinerWithAppend() {
        List<String> list = Arrays.asList("Hello", "World", "Test", null);
        StringBuilder sb = Joiner.on(",").skipNulls().appendTo(new StringBuilder(), list);
        assertThat(sb.toString(), equalTo("Hello,World,Test"));
    }

    @Test
    public void testJoinerToMap() {
        HashMap<Object, Object> map = new LinkedHashMap<>();
        map.put("hello", "Hello");
        map.put("word", "WORD");
        map.put("test", "Test");
        String join = Joiner.on(",").withKeyValueSeparator("=").join(map);
        assertThat(join, equalTo("hello=Hello,word=WORD,test=Test"));
    }
}
