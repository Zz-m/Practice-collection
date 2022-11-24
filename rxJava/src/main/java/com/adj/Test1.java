package com.adj;

import io.reactivex.rxjava3.core.Observable;

public class Test1 {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.just(1, 2, 3).replay(2).autoConnect();

        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);
        observable.subscribe(System.out::println);
    }
}
