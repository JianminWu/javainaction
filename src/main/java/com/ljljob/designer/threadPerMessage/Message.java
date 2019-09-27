package com.ljljob.designer.threadPerMessage;

import lombok.Data;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 15:52
 * @Function:
 * @Version 1.0
 */
@Data
public class Message {

    private String val;

    public Message(String val) {
        this.val = val;
    }
}
