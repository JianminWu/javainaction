package com.ljljob.designer.context;

/**
 * @Author: wujianmin
 * @Date: 2019/9/26 14:39
 * @Function:
 * @Version 1.0
 */
public class ActionContext {

    private ActionContext() {
    }

    private static final ThreadLocal<Context> threadLocal = ThreadLocal.withInitial(() -> new Context());

    private static class InstanceHolder {
        private final static ActionContext INSTANCE = new ActionContext();
    }

    public static ActionContext getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public Context getContext() {
        return threadLocal.get();
    }

    public void setContext(Context context){
        this.threadLocal.set(context);
    }
}
