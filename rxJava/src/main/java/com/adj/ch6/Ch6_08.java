package com.adj.ch6;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch6_08 {
    public static void main(String[] args) {
        Observable<Integer> lengths = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .subscribeOn(Schedulers.computation())
                .map(Util::intenseCalculation)
                .map(String::length)
                .share();

        lengths.subscribe(i -> System.out.println("O1 Received " + i + " on thread " + Thread.currentThread().getName()));
        lengths.subscribe(i -> System.out.println("O2 Received " + i + " on thread " + Thread.currentThread().getName()));

        Util.sleep(10000);
    }
}
