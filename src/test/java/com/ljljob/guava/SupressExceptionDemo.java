package com.ljljob.guava;

/**
 * @Author: wujianmin
 * @Date: 2019/9/5 15:37
 * @Function:
 * @Version 1.0
 */
public class SupressExceptionDemo {

    public static void main(String[] args) {
        Throwable t = null;
        try {
            throw new RuntimeException("try exception");
        } catch (Exception e) {
            t = e;
            throw e;
        } finally {
            try {
                throw new RuntimeException("finally exception");
            } catch (Exception e) {
                t.addSuppressed(e);
            }

        }
    }
}
