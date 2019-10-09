package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:04
 * @Function:
 * @Version 1.0
 */
public class Servant implements ActiveObject {

    @Override
    public Result makeString(int count, char c) {
        char[] buf = new char[count];
        for (int i = 0; i < count; i++) {
            buf[i] = c;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult<String>(new String(buf));
    }

    @Override
    public void displayStr(String text) {
        try {
            System.out.println("Display: " + text);
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
