package com.ljljob.concurrent.juc.phaser;

import com.ljljob.classloader.chapter1.Parent;

import java.util.concurrent.Phaser;

/**
 * @Author: wujianmin
 * @Date: 2019/10/18 16:43
 * @Function:
 * @Version 1.0
 * forceTermination 强制中断
 */
public class PhaserExample4 {
    public static void main(String[] args) {
        final Phaser phaser = new Phaser(2);

        new Thread(){
            @Override
            public void run() {
                phaser.arriveAndAwaitAdvance();
            }
        }.start();

        phaser.forceTermination();
    }

}
