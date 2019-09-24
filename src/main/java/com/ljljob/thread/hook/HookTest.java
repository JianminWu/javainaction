package com.ljljob.thread.hook;



import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.google.common.base.Charsets;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/20 10:27
 * @Function:
 * @Version 1.0
 */
public class HookTest {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process exec = runtime.exec("java --version");
//            InputStream inputStream = exec.getInputStream();
//            String string = IoUtil.read(inputStream).toString(Charsets.UTF_8);
//            System.out.println(string);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("I will Shutdown!!");
        }));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
