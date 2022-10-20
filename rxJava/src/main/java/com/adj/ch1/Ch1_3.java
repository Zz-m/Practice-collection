package com.adj.ch1;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Ch1_3 {
    public static void main(String[] args) {
        Observable<Long> secondIntervals = Observable.interval(1, TimeUnit.SECONDS);
        secondIntervals.subscribe(i -> {
            System.out.println(i);
            System.out.println(Thread.currentThread().getName());
        });
        sleep(5_000);
        System.out.println(Thread.currentThread().getName());
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
