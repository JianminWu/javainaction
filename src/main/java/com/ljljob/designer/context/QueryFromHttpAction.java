package com.ljljob.designer.context;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 14:48
 * @Function:
 * @Version 1.0
 */
public class QueryFromHttpAction {

    public void execute() {
        Context context = ActionContext.getInstance().getContext();
        String name = context.getName();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ret = String.format("query id from %s", name);
        context.setCardId(ret);
    }
}
