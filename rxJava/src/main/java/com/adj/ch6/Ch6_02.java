package com.adj.ch6;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;

public class Ch6_02 {
    public static void main(String[] args) {
        Observable.just("Alpha", "Beta", "Gamma")
                .map(Util::intenseCalculation)
                .subscribe(System.out::println);

        Observable.range(1, 3)
                .map(Util::intenseCalculation)
                .subscribe(System.out::println);
    }
}
