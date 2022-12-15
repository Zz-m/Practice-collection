package com.adj;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Test3 {
    public static void main(String[] args) {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);

        source.observeOn(Schedulers.io())
                .map(i -> {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " map1: " + i);
                    return i;
                })
                .observeOn(Schedulers.computation())
                .map(i -> {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " map2: " + i);
                    return i;
                }).subscribe();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
