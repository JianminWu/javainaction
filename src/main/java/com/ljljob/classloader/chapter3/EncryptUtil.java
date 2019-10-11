package com.ljljob.classloader.chapter3;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * @Author: wujianmin
 * @Date: 2019/10/11 15:01
 * @Function:
 * @Version 1.0
 */
public final class EncryptUtil {

    private EncryptUtil() {
    }

    public static final byte ENCRYPT_KEY = 0x01;

    public static byte[] encrypt(String path) {
        byte[] bytes = null;
        try {
            byte[] source = bytes = Files.asByteSource(new File(path)).read();
            bytes = new byte[source.length];
            for (int i = 0; i < source.length; i++) {
                byte b = source[i];
                bytes[i] = (byte) (b ^ ENCRYPT_KEY);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] encrypt(byte[] source) {
        byte[] bytes = null;
        try {
            bytes = new byte[source.length];
            for (int i = 0; i < source.length; i++) {
                byte b = source[i];
                bytes[i] = (byte) (b ^ ENCRYPT_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] encryptStr(String str) {
        byte[] bytes = null;
        try {
            byte[] source = bytes = str.getBytes();
            bytes = new byte[source.length];
            for (int i = 0; i < source.length; i++) {
                byte b = source[i];
                bytes[i] = (byte) (b ^ ENCRYPT_KEY);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void main(String[] args) {
//        byte[] hellos = encryptStr("hello wor;ld");
//        System.out.println(new String(hellos));
//        System.out.println(new String(encryptStr(new String(hellos))));
        byte[] encrypt = encrypt("D:\\test_class_load\\chapter1\\com\\ljljob\\classloader\\chapter2\\SimpleObject.class");
        try {
            Files.asByteSink(new File("D:\\test_class_load\\chatper3\\com\\ljljob\\classloader\\chapter3\\SimpleObject.class")).write(encrypt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
