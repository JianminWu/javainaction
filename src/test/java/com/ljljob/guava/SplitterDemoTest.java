package com.ljljob.guava;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


/**
 * @Author: wujianmin
 * @Date: 2019/9/2 15:35
 * @Function:
 * @Version 1.0
 */
public class SplitterDemoTest {

    @Test
    public void testSplitter(){
        String s = "Hello,World,Test";
        List<String> list = Splitter.on(",").splitToList(s);
        assertThat(list.size(),equalTo(3));
        assertThat(list.get(0),equalTo("Hello"));
        assertThat(list.get(1),equalTo("World"));
        assertThat(list.get(2),equalTo("Test"));
    }

    @Test
    public void testSplitterWithNull(){
        String s = "Hello,World,Test,,,,,Halo";
        List<String> list = Splitter.on(",").omitEmptyStrings().splitToList(s);
        assertThat(list.size(),equalTo(4));
        assertThat(list.get(0),equalTo("Hello"));
        assertThat(list.get(1),equalTo("World"));
        assertThat(list.get(3),equalTo("Halo"));
    }

    @Test
    public void testSplitterToMap(){
        String s = "hello=Hello,word=WORD,test=Test";
        Map<String, String> map = Splitter.on(",").withKeyValueSeparator("=").split(s);
        assertThat(map.size(),equalTo(3));
        System.out.println(map);
    }

}
