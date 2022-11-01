package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Ch5_10 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> ints = Observable.interval(1, TimeUnit.SECONDS)
                .publish().refCount();

        ints.take(5)
                .subscribe(l -> System.out.println("O1: " + l));

        Thread.sleep(3000);

        ints.take(2)
                .subscribe(l -> System.out.println("O2: " + l));

        Thread.sleep(3000);

        ints.subscribe(l -> System.out.println("O3: " + l));
        Thread.sleep(5000);
    }
}
