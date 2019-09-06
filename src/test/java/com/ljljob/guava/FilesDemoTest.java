package com.ljljob.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/4 17:02
 * @Function:
 * @Version 1.0
 */
public class FilesDemoTest {

    private final String SOURCE = "E:\\java_project\\zds\\javainaction\\src\\test\\resources\\filetest\\source.txt";
    private final String TARGET = "E:\\java_project\\zds\\javainaction\\src\\test\\resources\\filetest\\target.txt";

    @Test
    public void testFileCopy() throws Exception {
        Files.copy(new File(SOURCE), new File(TARGET));
        assertThat(new File(TARGET).exists(), equalTo(true));
    }

    @Test
    public void testCheckSignatures() throws IOException {
        File sourceFile = new File(SOURCE);
        HashCode sHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        File targetFile = new File(TARGET);
        Files.copy(sourceFile, targetFile);
        HashCode tHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        try {
            assertThat(sHashCode, equalTo(tHashCode));
        } finally {
            File file = new File(TARGET);
            file.deleteOnExit();
        }
    }

    @Test
    public void testMoveFile() throws IOException {
        File to = new File(TARGET);
        File from = new File(SOURCE);
        try {
            Files.move(from, to);
            assertThat(to.exists(), equalTo(true));
        } finally {
            Files.move(to, from);
        }
    }

    @Test
    public void testWrite() throws Exception{
        final String expectedString = "today we will share the guava io knowledge.\n" +
                "but only for the basic usage. if you wanted to get the more details information\n" +
                "please read the guava document or source code.\n" +
                "\n" +
                "The guava source code is very cleanly and nice.";
        Files.asCharSink(new File(SOURCE),Charsets.UTF_8).write(expectedString);
    }

    @Test
    public void testToString() throws IOException {

        final String expectedString = "today we will share the guava io knowledge.\n" +
                "but only for the basic usage. if you wanted to get the more details information\n" +
                "please read the guava document or source code.\n" +
                "\n" +
                "The guava source code is very cleanly and nice.";
        List<String> lines = Files.readLines(new File(SOURCE), Charsets.UTF_8);
        String actually = Joiner.on("\n").join(lines);
        assertThat(actually,equalTo(expectedString));
    }

    @Test
    public void testToProcessString() throws IOException {
        LineProcessor<List<Integer>> processor = new LineProcessor<List<Integer>>() {
            private final List<Integer> lines = Lists.newArrayList();
            @Override
            public boolean processLine(String line) throws IOException {
                lines.add(line.length());
                return true;
            }

            @Override
            public List<Integer>  getResult() {
                return lines;
            }
        };
        List<Integer> result = Files.asCharSource(new File(SOURCE), Charsets.UTF_8).readLines(processor);
        result.stream().forEach(System.out::println);
    }

    @Test
    public void testFileAppendAndTouch() throws IOException {
        File source = new File(SOURCE);
        File target = new File(TARGET);
        Files.touch(target);
        Files.asCharSink(target,Charsets.UTF_8,FileWriteMode.APPEND).write("hehe1");
        Files.asCharSink(target,Charsets.UTF_8,FileWriteMode.APPEND).write("hehe2");
        Files.asCharSink(target,Charsets.UTF_8,FileWriteMode.APPEND).write("hehe3");
        assertThat(Files.asCharSource(target, Charsets.UTF_8).read(),equalTo("hehe1hehe2hehe3"));
    }

    @Test
    public void testPreRecursive() {
        Files.fileTraverser().depthFirstPreOrder(new File("E:\\java_project\\zds\\javainaction\\src")).forEach(System.out::println);
    }

    @Test
    public void testPostRecursive() {
        Files.fileTraverser().depthFirstPostOrder(new File("E:\\java_project\\zds\\javainaction\\src")).forEach(System.out::println);
    }

    @Test
    public void testBreadthFirstRecursive() {
        Files.fileTraverser().breadthFirst(new File("E:\\java_project\\zds\\javainaction\\src")).forEach(System.out::println);
    }


    @Test
    public void testRecursiveChildren() {
        Files.fileTraverser().breadthFirst(new File("E:\\java_project\\zds\\javainaction\\src")).forEach(t->{
        });

    }


    @After
    public void tearDown() {
        File file = new File(TARGET);
        file.deleteOnExit();
    }
}
