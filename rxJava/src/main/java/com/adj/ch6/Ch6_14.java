package com.adj.ch6;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch6_14 {
    public static void main(String[] args) {
        Observable.just("WHISKEY/27653/TANGO","6555/BRAVO","232352/5675675/FOXTROT")
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> System.out.println("Received " + s + " on thread " + Thread.currentThread().getName()))
                .observeOn(Schedulers.single())
                .flatMap(s -> Observable.fromArray(s.split("/")))
                .doOnNext(s -> System.out.println("Received " + s + " on thread " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .filter(s -> s.matches("[0-9]+"))
                .doOnNext(s -> System.out.println("Received " + s + " on thread " + Thread.currentThread().getName()))
                .map(Integer::valueOf)
                .observeOn(Schedulers.newThread())
                .reduce((total, next) -> total + next)
                .subscribe(i -> System.out.println("Received " + i + " on thread " + Thread.currentThread().getName()));

        Util.sleep(1000);
    }
}
