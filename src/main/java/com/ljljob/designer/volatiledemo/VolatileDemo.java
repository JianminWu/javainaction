package com.ljljob.designer.volatiledemo;

/**
 * @Author: wujianmin
 * @Date: 2019/9/24 9:55
 * @Function:
 * @Version 1.0
 * @Description:
 * 多线程核心三要素 1.原子性 2.可见性 3.有序性
 * volatile 解决了其中的可见性和有序性
 * 1.可见性 volatile使得多线程在改变volatile修改的变量的时候,volatile让CPU高速缓存的数据失效,让程序从主存重新读取
 * 2.有序性 volatile关键字解决了在JVM优化重排序改变执行顺序,volatile保证了取消了JVM的重排序让程序按代码顺序执行
 *
 * 使用场景:
 *  1. 多线程改变某一个状态变量 volatile boolean flag = true;
 *  2. 为保证程序执行的顺序性
 *      volatile boolean isCreate = false;
 *      init{
 *          doCreateMethod();
 *          isCreate=true;
 *      }
 */
public class VolatileDemo {

    private static int INIT_VAL = 0;
//    private static volatile int INIT_VAL = 0;

    private static int MAX = 10;

    public static void main(String[] args) {
        new Thread("reader") {
            @Override
            public void run() {
                int localVal = INIT_VAL;
                while (localVal < MAX) {
                    if (localVal != INIT_VAL) {
                        System.out.println(String.format("INIT_VAL is charged to %d", localVal));
                        localVal = INIT_VAL;
                    }
                }
            }
        }.start();

        new Thread("write") {
            @Override
            public void run() {
                int localVal = INIT_VAL;
                while (localVal < MAX) {
                    System.out.println(String.format("INIT_VAL is writer to %d", ++localVal));
                    INIT_VAL = localVal;
                }
            }
        }.start();

    }
}
