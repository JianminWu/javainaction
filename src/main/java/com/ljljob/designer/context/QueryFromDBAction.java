package com.ljljob.designer.context;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 14:45
 * @Function:
 * @Version 1.0
 */
public class QueryFromDBAction {

    public void execute() {
        Context context = ActionContext.getInstance().getContext();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        context.setName("query_name " + Thread.currentThread().getName());
    }

}
