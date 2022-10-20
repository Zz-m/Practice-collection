package com.adj.ch1;

import io.reactivex.rxjava3.core.Observable;

public class Ch1_4 {
    public static void main(String[] args) {
        Observable<String> observable = Observable.create(emitter -> {
           emitter.onNext("1");
           emitter.onNext("2");
           emitter.onNext("3");
           throw new RuntimeException("my e");
        });

        observable.doOnNext(System.out::println)
                .doOnError(e->{System.out.println("print s get err");})
                .map(s->s.length())
                .onErrorReturn(throwable -> {return 2;})
                .doOnNext(l -> {System.out.println("length: " + l);})
                .doOnError(e->{System.out.println("print l get err");})
                .subscribe(integer -> {}, throwable -> {});
    }
}
