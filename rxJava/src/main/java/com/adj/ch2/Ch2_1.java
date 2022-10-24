package com.adj.ch2;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.observables.ConnectableObservable;

public class Ch2_1 {
    public static void main(String[] args) {
        ConnectableObservable<String> source = Observable.just("Alpha", "Beta", "Gamma").publish();

        source.subscribe(s -> {
            System.out.println("O1:" + s + " " + Thread.currentThread().getName());
        });
        source.map(String::length)
                .subscribe(i -> System.out.println("O2: " + i));
        source.connect();
        source.subscribe(s -> {
            System.out.println("O3:" + s + " " + Thread.currentThread().getName());
        });
        source.connect();
    }
}
