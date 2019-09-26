package com.ljljob.designer.immutable;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 10:05
 * @Function:
 * @Version 1.0
 * 不可变对象
 * 1. all field modified by private and final
 * 2. class modified by final
 * 3. no setter methods
 * 4. thread safe
 */
public final class ImmutableUser {

    private final String name;

    private final Integer age;

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public ImmutableUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
