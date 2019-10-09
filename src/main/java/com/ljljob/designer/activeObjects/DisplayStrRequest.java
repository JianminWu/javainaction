package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:21
 * @Function:
 * @Version 1.0
 */
public class DisplayStrRequest extends MethodRequest {

    private final Servant servant;

    private final String text;

    public DisplayStrRequest(Servant servant, String text) {
        super(servant, null);
        this.servant = servant;
        this.text = text;
    }

    @Override
    public void execute() {
        servant.displayStr(text);
    }
}
