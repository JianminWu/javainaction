package com.ljljob.guava.cache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @Author: wujianmin
 * @Date: 2019/11/1 11:48
 * @Function:
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class Employee {

    @Tolerate
    public Employee(){}

    private String name;

    private String addr;

    private final byte[] bytes = new byte[1024*1024];

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(this +" will be gc");
    }
}
