package com.ljljob.concurrent.juc.countdownlatch;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Author: wujianmin
 * @Date: 2019/10/15 11:14
 * @Function:
 * @Version 1.0
 */
public class CountDownLatchExample2 {


    private static final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        List<Table> tables = fetchTable();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (Table table : tables) {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            TaskLine taskLine = new TaskLine(countDownLatch);
            executorService.execute(new CheckCountRunnable(table,taskLine));
            executorService.execute(new CheckSchemaRunnable(table,taskLine));
        }
    }

    public static List<Table> fetchTable() {
        List<Table> tables = Lists.newArrayList();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            tables.add(new Table("table-" + i));
        });
        return tables;
    }

    static class Event {
        int id;

        public Event(int id) {
            this.id = id;
        }
    }

    interface Watcher {
        void done(Table table);
    }

    static class TaskLine implements Watcher {
        private CountDownLatch countDownLatch;

        public TaskLine(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void done(Table table) {
            countDownLatch.countDown();
            if (countDownLatch.getCount() == 0) {
                System.out.println(table.getName() + " has finished " + table);
            }
        }
    }

    @Data
    static class Table {
        private String name;

        private int sourceCount = 10000;

        private int targetCount;

        private String sourceSchema = "<data><col type='str' data='strtest'/></data>";

        private String targetSchema;

        public Table(String name) {
            this.name = name;
        }
    }

    static class CheckCountRunnable implements Runnable {

        private Table table;

        private TaskLine taskLine;

        public CheckCountRunnable(Table table, TaskLine taskLine) {
            this.table = table;
            this.taskLine = taskLine;
        }

        @Override
        public void run() {
            table.setTargetCount(table.getSourceCount());
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            taskLine.done(table);
        }
    }

    static class CheckSchemaRunnable implements Runnable {

        private Table table;

        private TaskLine taskLine;

        public CheckSchemaRunnable(Table table, TaskLine taskLine) {
            this.table = table;
            this.taskLine = taskLine;
        }

        @Override
        public void run() {
            table.setTargetSchema(table.getSourceSchema());
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            taskLine.done(table);
        }
    }
}
