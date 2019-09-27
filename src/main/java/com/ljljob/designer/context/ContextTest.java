package com.ljljob.designer.context;

import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 14:50
 * @Function:
 * @Version 1.0
 */
public class ContextTest {

    public static void main(String[] args) {
        IntStream.rangeClosed(0,4).forEach(i->{
            Context context = new Context();
            new Thread(new ExecutionTask(context)).start();
        });
    }
}
