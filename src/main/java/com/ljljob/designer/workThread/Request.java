package com.ljljob.designer.workThread;

import lombok.Data;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 10:18
 * @Function:
 * @Version 1.0
 */
@Data
public class Request {
    private String val;

    public Request(String val) {
        this.val = val;
    }
}
