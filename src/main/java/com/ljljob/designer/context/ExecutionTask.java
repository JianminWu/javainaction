package com.ljljob.designer.context;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 14:51
 * @Function:
 * @Version 1.0
 */
public class ExecutionTask implements Runnable {
    private Context context;

    public ExecutionTask(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        ActionContext.threadLocal.set(this.context);
        QueryFromDBAction queryFromDBAction = new QueryFromDBAction();
        queryFromDBAction.execute();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryFromHttpAction queryFromHttpAction = new QueryFromHttpAction();
        queryFromHttpAction.execute();
        System.out.println(context);

    }
}
