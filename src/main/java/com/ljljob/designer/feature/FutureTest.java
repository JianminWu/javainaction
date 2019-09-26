package com.ljljob.designer.feature;

/**
 * @Author: wujianmin
 * @Date: 2019/9/25 15:46
 * @Function:
 * @Version 1.0
 */
public class FutureTest {


    public static void main(String[] args) throws Exception {
        futureWithConsumer();
        System.out.println("===============");
        singleFuture();
    }

    private static void futureWithConsumer() {
        FutureService<String> futureService = new FutureService<>();
        futureService.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "aysn task FINISH";
        }, System.out::println);
        System.out.println("do my task !!");
        System.out.println("do my task !!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do my task FINISH!!");
    }

    private static void singleFuture() throws InterruptedException {
        FutureService<String> futureService = new FutureService<>();

        Future<String> future = futureService.submit(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "aysn task FINISH";
        });

        System.out.println("do my task !!");
        System.out.println("do my task !!");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do my task FINISH!!");

        System.out.println(future.get());
    }
}
