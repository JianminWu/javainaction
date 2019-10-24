package com.ljljob.concurrent.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author: wujianmin
 * @Date: 2019/10/24 9:47
 * @Function:
 * @Version 1.0
 * Async 和没有 Async区别在于用没用default 的forkjoinPool
 * 1. {@link CompletableFuture#thenAccept(Consumer)} -> 传入consumer消费掉上流的结果 返回一个Void的completableStage
 * 2. {@link CompletableFuture#thenApply(Function)} -> 传入function消费上流结果产生新的结果返回给下流
 * 3. {@link CompletableFuture#thenAcceptBoth(CompletionStage, BiConsumer)} -> 传入一个completionStage BiConsumer(stage1_result,stage2_result) 返回Void CompletableStage
 * 4. {@link CompletableFuture#thenAcceptBothAsync(CompletionStage, BiConsumer)} 同上采用默认forkjoin useCommonPool ForkJoinPool.commonPool()
 * 5. {@link CompletableFuture#runAfterBoth(CompletionStage, Runnable)} 执行完2个stage之后,传入一个runnable不管之前2个stage的返回值
 * 6. xxxEither 某一个stage完成之后执行
 * 7. {@link CompletableFuture#thenCombine(CompletionStage, BiFunction)} 将2个stage合并成一个新的stage BiFunction(stage1_result,stage2_result) 返回一个整合之后的stage_result
 * 8. {@link CompletableFuture#thenCompose(Function)}  将一个stage交给另外一个stage进行处理返回行的stage 类似stream中的map
 * 9. {@link CompletableFuture#handle(BiFunction)} 类似一个非terminated 的whenComplete BiFunction(stage_result,stage_exception)
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws InterruptedException {
//        testThenAccept();
//        testThenAcceptAsync();
//        testThenApply();
//        testThenAcceptBoth();
//        testRunAfterBoth();
//        testRunAfterBothAsync();
//        testEither();
//        testCombine();
//        testCompose();
        testHandle();
        Thread.currentThread().join();
    }

    private static void testThenAccept() {
        CompletableFuture.supplyAsync(() -> "hello").thenAccept(t -> System.out.println(t.length()));
    }

    private static void testThenAcceptAsync() {
        CompletableFuture.supplyAsync(() -> {
            sleep(ThreadLocalRandom.current().nextInt(3));
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().hashCode());
            return "hello";
        }).thenAcceptAsync(t -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println(Thread.currentThread().hashCode());
            System.out.println("123");
//            System.out.println(t);
        });
    }

    private static void testThenApply() {
        CompletableFuture.supplyAsync(() -> "hello").thenApply(t -> t.length()).whenComplete((t, e) -> System.out.println(t));
    }

    private static void testThenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> "hello").thenAcceptBoth(CompletableFuture.supplyAsync(() -> "world"), (t1, t2) -> {
            System.out.println(t1 + " " + t2);
        });
    }

    private static void testRunAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            System.out.println("stage1");
            return "hello";
        }).runAfterBoth(CompletableFuture.supplyAsync(() -> {
            System.out.println("stage2");
            return "world";
        }), () -> System.out.println("everything has done"));
    }

    private static void testRunAfterBothAsync() {
        CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("stage1");
            return "hello";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("stage2");
            return "world";
        }), () -> System.out.println("everything has done"));
    }

    private static void testEither() {
        CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("stage1");
            return "hello";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("stage2");
            return "world";
        }), () -> {
            System.out.println("someone has done");
            System.out.println("=====================");
        });

        CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("stage1");
            return "hello";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("stage2");
            return "world";
        }), (t) -> "someone has done" + t).whenComplete((t, e) -> {
            System.out.println(t);
            System.out.println("=====================");
        });

        CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("stage1");
            return "hello";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            System.out.println("stage2");
            return "world";
        }), (t) -> System.out.println("someone has done " + t)).whenComplete((t, e) -> {
            System.out.println("=====================");
        });

    }

    public static void testCombine() {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenCombine(CompletableFuture.supplyAsync(() -> 111), (t1, t2) -> t1 + " " + t2)
                .whenComplete((t, e) -> {
                    System.out.println(t);
                });
    }

    public static void testCompose() {
        CompletableFuture.supplyAsync(() -> "hello")
                .thenCompose((t) -> CompletableFuture.supplyAsync(() -> t + " world"))
                .thenCompose((t) -> CompletableFuture.supplyAsync(() -> t + 1111))
                .whenComplete((t, e) -> System.out.println(t));
    }

    public static void testHandle() {
        CompletableFuture.supplyAsync(() -> "hello")
                .handle((t, e) -> t + 111)
                .handle((t, e) -> Integer.valueOf(t))
                .handle((t, e) -> {
                    if (null != e) {
//                        e.printStackTrace();
                        System.out.println("this is a error");
                    }
                    return t;
                });
    }


    public static void sleep(long time) {
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
