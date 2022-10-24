package com.adj.ch2;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

import java.util.concurrent.TimeUnit;

public class Ch2_15 {
    public static void main(String[] args) throws InterruptedException {
        Observable.interval(1, TimeUnit.MICROSECONDS)
                .subscribe(i->System.out.println("RE: " + i));
        Observable.defer(()->Observable.range(1, 3));
        Thread.sleep(3000);
        Observable.create(emitter -> {

        });
        Observable source = Observable.just("Alpha");

        Single single = Single.just("from single");

        single.subscribe(s->System.out.println("get: " + s), System.out::println);

    }
}
