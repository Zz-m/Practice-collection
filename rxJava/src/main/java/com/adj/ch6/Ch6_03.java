package com.adj.ch6;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch6_03 {
    public static void main(String[] args) {
        Observable.just("Alpha", "Beta", "Gamma")
                .subscribeOn(Schedulers.computation())
                .map(Util::intenseCalculation)
                .subscribe(System.out::println);

        Observable.range(1, 3)
                .subscribeOn(Schedulers.computation())
                .map(Util::intenseCalculation)
                .subscribe(System.out::println);

        Util.sleep(10000);
    }
}
