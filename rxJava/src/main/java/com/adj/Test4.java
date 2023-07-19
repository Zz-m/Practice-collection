package com.adj;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class Test4 {
    public static void main(String[] args) throws InterruptedException {
        BehaviorSubject<Integer> b1 = BehaviorSubject.create();

        Observable<Integer> o1 = b1.map(i -> i + 1);

        o1.subscribe(i -> {
            System.out.println("sub1 get: " + i);
        });

        b1.onNext(1);

        o1.subscribe(i -> {
           System.out.println("Sub2 get:" + i);
        });

        Thread.sleep(2000);
    }
}
