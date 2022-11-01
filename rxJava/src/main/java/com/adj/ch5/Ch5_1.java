package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

public class Ch5_1 {
    public static void main(String[] args) {
        ConnectableObservable<Integer> ints = Observable.range(1, 3).publish();
        ints.subscribe(i -> System.out.println("O1: " + i));
        ints.subscribe(i -> System.out.println("O2: " + i));

        ints.connect();
    }
}
