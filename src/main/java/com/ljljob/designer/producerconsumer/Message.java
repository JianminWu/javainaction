package com.ljljob.designer.producerconsumer;

import lombok.Data;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 11:51
 * @Function:
 * @Version 1.0
 */
@Data
public class Message {

    private String msg;

    public Message(String msg) {
        this.msg = msg;
    }
}
