package com.adj;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

public class Test2 {
    public static void main(String[] args) {
        Observable<Long> recordPublisher = Observable.interval(1, TimeUnit.SECONDS);

        PublishSubject<Long> goodRecordSubject = PublishSubject.create();
        PublishSubject<Long> badRecordSubject = PublishSubject.create();

        //Record Match Process
        recordPublisher.subscribe(i -> {
            if (i % 2 == 0) {
                goodRecordSubject.onNext(i);
            } else {
                badRecordSubject.onNext(i);
            }
        });

        //now deal with good record;
        goodRecordSubject.subscribe(
                i -> System.out.println("get good record: " + i)
        );

        //Deal with bad record;
        badRecordSubject.subscribe(
                i -> System.out.println("get bad record: " + i)
        );

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
