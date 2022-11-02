package com.adj.ch6;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Ch6_20 {
    public static void main(String[] args) {
        Disposable d = Observable.interval(1, TimeUnit.SECONDS)
                .map(Util::intenseCalculation)
                .doOnDispose(() -> System.out.println("Disposing on thread " + Thread.currentThread().getName()))
                .subscribe(i -> System.out.println("Received " + i + " on thread " + Thread.currentThread().getName()), e -> System.out.println("get error"));

        Util.sleep(3000);
        long before = System.nanoTime();
        d.dispose();
        long after = System.nanoTime();
        Util.sleep(3000);
        System.out.println(after - before);
    }
}
