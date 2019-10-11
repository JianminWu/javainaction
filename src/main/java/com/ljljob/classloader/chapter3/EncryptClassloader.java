package com.ljljob.classloader.chapter3;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author: wujianmin
 * @Date: 2019/10/11 15:01
 * @Function:
 * @Version 1.0
 */
public class EncryptClassloader extends ClassLoader {

    private final String DEFAULT_DIR = "D:/test_class_load/chatper3/";

    private String dir = DEFAULT_DIR;

    private String name;

    private ClassLoader parent;

    public EncryptClassloader() {
        super();
    }

    public EncryptClassloader(ClassLoader parent) {
        super(parent);
        this.parent = parent;
    }


    public EncryptClassloader(String name) {
        this.name = name;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = loadClassBytes(name);
        if (Objects.isNull(classBytes) || classBytes.length == 0) {
            throw new ClassNotFoundException(String.format("%s class file not found!", name));
        }

        return this.defineClass(name, classBytes, 0, classBytes.length);
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * 核心逻辑在读取class文件的byte数组时进行解密处理
     *
     * @param name
     * @return
     */
    private byte[] loadClassBytes(String name) {
        try {
            File file = new File(dir + "/" + name.replaceAll("\\.", "/") + ".class");
            System.out.println(file.exists());
            byte[] read = Files.asByteSource(file).read();
            return EncryptUtil.encrypt(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
