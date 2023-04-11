package com.adj.date;

import java.time.*;

public class DateTest {

    public static void main(String[] args) {

        LocalDateTime localDateTime = LocalDateTime.now();

        long l = System.currentTimeMillis() ;

        System.out.println("L: " + l);

        double f = l;

        System.out.println("F: " + f);

        long l2 = (long) f;

        System.out.println("L2: " + l2);

        Instant instant = Instant.ofEpochMilli(l );
        Instant instant2 = Instant.ofEpochMilli(l2 );


        System.out.println(instant);
        System.out.println(instant2);

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Period period = Period.between(yesterday, today);

        System.out.println("period: " + period);
        System.out.println("period days: " + period.getDays());

        int milliSecondsOfADay = 24 * 60 * 60 * 1000;

        System.out.println("milliOfADay: " + milliSecondsOfADay);
    }
}
