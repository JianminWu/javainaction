package com.ljljob.guava.cache;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: wujianmin
 * @Date: 2019/11/1 11:48
 * @Function:
 * @Version 1.0
 * https://www.cnblogs.com/huajiezh/p/5835618.html
 */
public class ReferenceExample {

    private static final AtomicInteger index = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
//        testHardReference();
//        testSoftReference();
//        testWeakReference();
        testPhantomReference();
    }

    /**
     * 幻影引用
     * 一般使用在需要调用gc回收之后的后续操作
     * 虚引用和前面的软引用、弱引用不同，它并不影响对象的生命周期。在java中用java.lang.ref.PhantomReference类表示。如果一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收。
     　　要注意的是，虚引用必须和引用队列关联使用，当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中。程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
     referenceQueue.remove() 是一个block方法
     * @throws InterruptedException
     */
    private static void testPhantomReference() throws InterruptedException {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        TestObj testObj = new TestObj(10);
        MyPhantomReference<TestObj> phantomReference = new MyPhantomReference<>(testObj,referenceQueue,10);
        testObj = null;
        System.out.println(phantomReference.get()); // null
        System.gc();
        ((MyPhantomReference)referenceQueue.remove()).doAction();

    }

    static class MyPhantomReference<Object> extends PhantomReference<Object>{

        private final int index;


        public MyPhantomReference(Object referent, ReferenceQueue<? super Object> q, int index) {
            super(referent, q);
            this.index = index;
        }

        public void doAction()
        {
            System.out.println("+++++++++++++++  The object " + index + " is GC. +++++++++++++++++");
        }
    }

    /**
     * 　弱引用也是用来描述非必需对象的，当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。
     * @throws InterruptedException
     */
    private static void testWeakReference() throws InterruptedException {
        List<WeakReference<TestObj>> container = new ArrayList<>();
        while (true){
            System.out.println("ojb-" + index.get() + "put in cache");
            WeakReference<TestObj> ref = new WeakReference<TestObj>(new TestObj(index.getAndIncrement()));
            container.add(ref);
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    /**
     * softReference 弱引用 在JVM检测到快OOM的时候会调用gc回收SoftReference资源,但因为GC回收是打标记,可能还未真正回收,也可能抛OOM异常
     *
     * @throws InterruptedException
     */
    private static void testSoftReference() throws InterruptedException {
        List<SoftReference<TestObj>> container = new ArrayList<>();
        while (true) {
            System.out.println("ojb-" + index.get() + "put in cache");
            SoftReference<TestObj> sr = new SoftReference(new TestObj(index.getAndIncrement()));
            container.add(sr);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * 强引用 直接new  强引用有引用变量指向时永远不会被垃圾回收，JVM宁愿抛出OutOfMemory错误也不会回收这种对象。
     */
    private static void testHardReference() throws InterruptedException {
        List<TestObj> container = new ArrayList<>(100);
        while (index.get() < 100) {
            if (index.get() < 100) {
                container.add(new TestObj(index.getAndIncrement()));
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }

    }


    static class TestObj {
        private final int index;

        public TestObj(int index) {
            this.index = index;
        }

        private byte[] bytes = new byte[1024 * 1024];

        @Override
        public String toString() {
            return "obj-" + index;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("obj-" + index + "===============has be gc");
        }
    }
}
