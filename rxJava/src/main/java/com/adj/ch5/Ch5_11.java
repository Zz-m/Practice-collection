package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Ch5_11 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS).publish()
                .autoConnect()
                .cache()
                .replay().autoConnect();

        ints.subscribe(l -> System.out.println("O1: " + l));

        Thread.sleep(3000);

        ints.subscribe(l -> System.out.println("O2: " + l));

        Thread.sleep(3000);
    }
}
