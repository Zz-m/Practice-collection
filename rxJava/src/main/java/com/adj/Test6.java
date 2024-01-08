package com.adj;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

import java.util.concurrent.TimeUnit;

public class Test6 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> o = Observable.interval(1, TimeUnit.SECONDS);
        Observable o2 = o.flatMap(i -> {
            int r = 0;
                    try {
                        r = (int) (i / (i % 3));
                    } catch (Exception e) {
                        r = -1;
                    }
                    return Observable.just(r);
                })
                .onErrorReturn(e -> {
                    System.out.println("get error inner:" + e);
                    return  -1;
                });

//        o2.onErrorReturn(o1 -> -1).subscribe(i -> System.out.println("get:" + i));
        o2.subscribe(i -> System.out.println("get:" + i), throwable -> {
            System.out.println("get error:" + throwable.toString());
        });

        Thread.sleep(5000000);


    }
}
