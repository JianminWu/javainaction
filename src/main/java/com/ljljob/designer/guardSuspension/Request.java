package com.ljljob.designer.guardSuspension;

import lombok.Getter;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 10:44
 * @Function:
 * @Version 1.0
 */
@Getter
public class Request {

    private String value;

    public Request(String value) {
        this.value = value;
    }
}
