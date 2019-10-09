package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 16:21
 * @Function:
 * @Version 1.0
 */
public class ActivateObjectsTest {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectsFactory.createActiveObject();
        // 两个MakeString的方法调用者
        new MakeStringClientThread("Alice", activeObject).start();
        new MakeStringClientThread("Bobby", activeObject).start();

        // DisplayString的方法调用者
        new DisplayClientThread("Chris", activeObject).start();
    }
}
