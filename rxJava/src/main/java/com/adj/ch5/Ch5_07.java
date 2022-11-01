package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.ThreadLocalRandom;

public class Ch5_07 {
    public static void main(String[] args) {
        Observable<Integer> rInts = Observable.range(1, 3)
                .map(i -> randomInt())
                .publish()
                .autoConnect(2);

        rInts.subscribe(i -> System.out.println("O1: " + i));

        rInts.reduce(0, Integer::sum)
                .subscribe(i -> System.out.println("O2: " + i));

        rInts.subscribe(i -> System.out.println("O3: " + i));
    }


    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt(100_000);
    }
}
