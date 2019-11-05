package com.ljljob.guava.monitor;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Monitor;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/30 9:58
 * @Function:
 * @Version 1.0
 */
public class MonitorExample {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) {
        MonitorDemo demo = new MonitorDemo();
        IntStream.rangeClosed(1,5).boxed().forEach(i->{
            new Thread(()->{
                while (COUNTER.get()<=10) {
                    int data = COUNTER.getAndIncrement();
                    demo.produce(data);
                    System.out.println(Thread.currentThread().getName()+"**** Produce -> "+ data);
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
        IntStream.rangeClosed(1,5).boxed().forEach(i->{
            new Thread(()->{
                while (true) {
                    Integer consume = demo.consume();
                    System.out.println(Thread.currentThread().getName()+" Consume -> "+consume);
                    try {
                        TimeUnit.MILLISECONDS.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        });
    }

    public static class MonitorDemo{
        private  final LinkedList<Integer> queue = Lists.newLinkedList();
        private  final Integer MAX_SIZE = 10;
        Monitor monitor = new Monitor();
        Monitor.Guard CAN_READ = monitor.newGuard(()->!queue.isEmpty());
        Monitor.Guard CAN_WRITE = monitor.newGuard(()->queue.size()<=MAX_SIZE);

        public void produce(Integer data){
            try {
                monitor.enterWhen(CAN_WRITE);
                queue.addLast(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                monitor.leave();
            }
        }

        public Integer consume(){
            Integer val =null;
            try {
                monitor.enterWhen(CAN_READ);
                val = queue.removeFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                monitor.leave();
            }
            return val;
        }
    }

    public static class synchronizedDemo{
        private  final LinkedList<Integer> queue = Lists.newLinkedList();
        private  final Integer MAX_SIZE = 10;

        public void produce(Integer data){
            synchronized (queue){
                while (queue.size() >= MAX_SIZE) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                queue.addLast(data);
                queue.notifyAll();
            }
        }

        public Integer consume(){
            synchronized (queue){
                while (queue.isEmpty()){
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer integer = queue.removeFirst();
                queue.notifyAll();
                return integer;
            }
        }
    }


    public static class lockDemo {
        private  final ReentrantLock lock = new ReentrantLock();
        private  final Condition readCondition = lock.newCondition();
        private  final Condition writeCondition = lock.newCondition();
        private  final LinkedList<Integer> queue = Lists.newLinkedList();
        private  final Integer MAX_SIZE = 10;


        public  void produce(Integer data) {
            try {
                lock.lock();
                while (queue.size() >= MAX_SIZE) {
                    writeCondition.await();
                }
                queue.addLast(data);
                readCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public  Integer consume() {
            Integer val = null;
            try {
                lock.lock();
                while (queue.isEmpty()) {
                    readCondition.await();
                }
                val = queue.removeFirst();
                writeCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            return val;
        }
    }

}
