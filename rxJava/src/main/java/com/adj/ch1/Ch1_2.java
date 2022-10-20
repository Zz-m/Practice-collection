package com.adj.ch1;

import io.reactivex.rxjava3.core.Observable;

public class Ch1_2 {
    public static void main(String[] args) {
        Observable<String> myStrings = Observable.just("Alpha", "Beta", "Gamma");
        myStrings.map(s -> s.length())
                .subscribe(System.out::println);
    }
}
