package com.ljljob.action;


import com.google.common.base.Stopwatch;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wujianmin
 * @Date: 2019/9/5 11:36
 * @Function:
 * @Version 1.0
 */
public class LocalDateDEMO {

    public static void main(String[] args) throws InterruptedException {
//        testLocalDate();
        testLocalTime();
        testParse();
    }

    public static void testLocalDate() {
        //        Stopwatch stopwatch = Stopwatch.createStarted();
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.get(ChronoField.DAY_OF_WEEK));
        System.out.println(localDate.plus(1, ChronoUnit.DAYS));
        System.out.println(localDate);
//        TimeUnit.SECONDS.sleep(3);
//        System.out.println(stopwatch.stop().elapsed(TimeUnit.SECONDS));
    }

    public static void testLocalTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getDayOfWeek());
        System.out.println(localDateTime.getDayOfYear());
        System.out.println(localDateTime.getMonth());
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getHour());
        System.out.println(localDateTime.getMinute());
        System.out.println(localDateTime.getSecond());
        System.out.println(localDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        System.out.println("************");
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm"));
        System.out.println(format);
        System.out.println("************");
    }

    public static void testParse(){
        String datetime= "1993-04-04 13:33:21";
//        LocalDateTime dateTime = LocalDateTime.parse(datetime,DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        LocalDateTime dateTime = LocalDateTime.parse(datetime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm"));
        System.out.println(dateTime);
    }
}
