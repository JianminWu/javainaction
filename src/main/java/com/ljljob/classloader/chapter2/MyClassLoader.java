package com.ljljob.classloader.chapter2;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: wujianmin
 * @Date: 2019/10/10 10:11
 * @Function:
 * @Version 1.0
 */
public class MyClassLoader extends ClassLoader {

    private String classLoaderName;

    private ClassLoader parent;

    private final static String DEFAULT_DIR = "D:/test_class_load/chapter1/";

    private String dir = DEFAULT_DIR;

    public MyClassLoader() {
        super();
    }

    public MyClassLoader(String classLoaderName, ClassLoader parent) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        byte[] classBytes;
        try {
            classBytes = this.loadCLassBytes(name);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("class load has failed");
        }
        if (Objects.isNull(classBytes) || classBytes.length == 0)
            throw new ClassNotFoundException("class load has failed");
        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    private byte[] loadCLassBytes(String name) throws IOException {
        String pathname = dir + name.replaceAll("\\.", "/") + ".class";
        File file = new File(pathname);
        System.out.println(file.exists());
        return Files.asByteSource(file).read();
    }
}
