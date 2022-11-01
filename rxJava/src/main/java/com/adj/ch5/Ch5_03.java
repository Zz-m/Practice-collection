package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.util.concurrent.ThreadLocalRandom;

public class Ch5_03 {
    public static void main(String[] args) {
        ConnectableObservable<Integer> ints = Observable.range(1, 3)
                .map(i -> randomInt())
                .publish();

        ints.subscribe(i -> System.out.println("O1: " + i));
        ints.reduce(0, Integer::sum)
                .subscribe(i -> System.out.println("O2: " + i));
        ints.connect();
    }

    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100_000);
    }
}
