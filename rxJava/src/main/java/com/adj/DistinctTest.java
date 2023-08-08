package com.adj;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.concurrent.TimeUnit;

public class DistinctTest {
    public static void main(String[] args) throws InterruptedException {
        BehaviorSubject<Boolean> behaviorSubject1 = BehaviorSubject.createDefault(false);
        BehaviorSubject<Boolean> behaviorSubject2 = BehaviorSubject.createDefault(false);
        BehaviorSubject<Boolean> behaviorSubject3 = BehaviorSubject.createDefault(false);

        behaviorSubject1.subscribe(b -> System.out.println("set B1:" + b));


        Observable.combineLatest(behaviorSubject1.distinctUntilChanged().doOnNext(b -> {
                    System.out.println("b1 out: " + b);
                })
                , behaviorSubject2.distinctUntilChanged().doOnNext(b -> {
                    System.out.println("b2 out: " + b);
                })
                , behaviorSubject3.distinctUntilChanged().doOnNext(b -> {
                    System.out.println("b3 out: " + b);
                })
                , (b_waves, b_rain, b_night) -> {
                    System.out.println("b_waves: " + b_waves + " b_rain: " + b_rain + " b_night: " + b_night);
                    int result = 0;
                    if (b_waves && !b_rain && !b_night) result = 1;
                    if (!b_waves && b_rain && !b_night) result = 2;
                    if (!b_waves && !b_rain && b_night) result = 3;
                    System.out.println("result:" + result);
                    return result;
                }).debounce(200, TimeUnit.MILLISECONDS).distinctUntilChanged().subscribe(integer -> {
            System.out.println("get result: " + integer);
        });

        System.out.println();
        System.out.println("test onNext false");
        behaviorSubject1.onNext(false);
        behaviorSubject1.onNext(false);
        behaviorSubject1.onNext(false);
        System.out.println();
        System.out.println();
        System.out.println("test onNext true");
        behaviorSubject1.onNext(true);
        System.out.println();

        System.out.println();
        System.out.println("FINISH");

        Thread.sleep(1000);
    }
}
