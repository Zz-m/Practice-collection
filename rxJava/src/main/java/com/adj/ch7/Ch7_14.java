package com.adj.ch7;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class Ch7_14 {
    public static void main(String[] args) {
        Observable<String> items = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota");

        Observable<String> processStrings = items.switchMap(s -> Observable.just(s)
                        .delay(Util.randomSleepTime(2000), TimeUnit.MILLISECONDS));

        processStrings.subscribe(System.out::println);

        Util.sleep(2000);
    }
}
