package com.adj;

import io.reactivex.Observable;

public class ObservableTest {
    public static void main(String[] args) {
        Observable<String> o = Observable.fromArray("a", "b", "c");
        o.subscribe(s -> System.out.println(s));
        o.subscribe(s -> System.out.println("2 " + s));
    }


}
