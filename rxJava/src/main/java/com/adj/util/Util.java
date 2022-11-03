package com.adj.util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {


    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + " is interrupted.");
        }
    }

    public static int randomSleepTime(int boundary) {
        assert (boundary > 0);
        return ThreadLocalRandom.current().nextInt(boundary);
    }
}
