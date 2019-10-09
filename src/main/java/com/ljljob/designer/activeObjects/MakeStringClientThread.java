package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 16:23
 * @Function:
 * @Version 1.0
 */
public class MakeStringClientThread extends Thread {

    private final String name;

    private final char fillChar;

    private final ActiveObject activeObject;

    public MakeStringClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.name = name;
        this.fillChar = name.charAt(0);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Result<String> result = activeObject.makeString(i + 1, fillChar);
                Thread.sleep(100);
                String value = result.getResult();
                System.out.println(Thread.currentThread().getName() + ":value=" + value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
