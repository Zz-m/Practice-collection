package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Ch5_09 {
    public static void main(String[] args) {
        Observable<Long> ints = Observable
                .interval(1, TimeUnit.SECONDS)
                .publish()
                .autoConnect();

        ints.subscribe(i -> System.out.println("O1: " + i + Thread.currentThread().getName()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ints.subscribe(i -> System.out.println("O2: " + i + Thread.currentThread().getName()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
