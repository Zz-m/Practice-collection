package com.adj;

import io.reactivex.rxjava3.core.Observable;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * <a href="https://stackoverflow.com/questions/74720155/observable-is-unicast-by-definition-but-why-it-seem-multicast-with-asynchronous/74806529#74806529">...</a>
 */
public class Test1 {
    public static void main(String[] args) {
        Observable<Integer> ob1 = Observable.just(500, 400)
                .delay(5000, TimeUnit.MILLISECONDS)
                .publish().autoConnect(); //publish operator make the observable hot and multicast!
        ob1.subscribe(i -> System.out.println(LocalTime.now() + " ob1: " + i));

        Observable.just(1).delay(1500, TimeUnit.MILLISECONDS)
                .subscribe(i -> {
                    System.out.println("delay once");
                    ob1.subscribe(aLong -> System.out.println(LocalTime.now() + " ob2: " + aLong));
                });

        Observable.just(1).delay(6000, TimeUnit.MILLISECONDS)
                .subscribe(i -> {
                    System.out.println("delay after the observable finish it's stream.");
                    ob1.subscribe(aLong -> System.out.println(LocalTime.now() + " ob3: " + aLong), Throwable::printStackTrace, () -> {
                        System.out.println("ob3 get completed!");
                    });
                });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
