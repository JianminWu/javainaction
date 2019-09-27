package com.ljljob.designer.balking;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @Author: wujianmin
 * @Date: 2019/9/27 11:46
 * @Function:
 * @Version 1.0
 */
public class BalkingData {
    private String fileName;
    private String content;
    private boolean changed;

    public BalkingData(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.changed = true;
    }

    public void change(String newContent) {
        this.changed = true;
        this.content = newContent;
    }

    public void save() {
        if (!changed) {
            return;
        }
        doSave();
    }

    private void doSave() {
        try (PrintWriter printWriter = new PrintWriter(fileName)) {
            printWriter.print(content);
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("The file has saved.");
    }
}
