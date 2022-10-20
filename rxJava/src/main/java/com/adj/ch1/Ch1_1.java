package com.adj.ch1;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class Ch1_1 {
    public static void main(String[] args) {
        Observable<String> myStrings = Observable.just("Alpha", "Beta", "Gamma");
        Disposable disposable = myStrings.subscribe(System.out::println);
    }
}
