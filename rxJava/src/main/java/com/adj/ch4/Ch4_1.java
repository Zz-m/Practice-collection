package com.adj.ch4;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.GroupedObservable;

public class Ch4_1 {
    public static void main(String[] args) {
        Observable<String> src1 = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Integer> src2 = Observable.range(1, 6)
                .doOnNext(integer -> {
                    System.out.println("O2: " + integer);
                })
                .doOnComplete(() -> System.out.println("O2 Complete"));

        Observable<GroupedObservable<Integer, String>> byLengths = src1.groupBy(String::length);

        byLengths.flatMapSingle(grp -> grp.reduce("", (x, y) -> x.equals("") ? y : x + ", " + y).map(s -> grp.getKey() + ": " + s))
                .subscribe(System.out::println);

    }
}
