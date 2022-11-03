package com.adj.ch7;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Observable;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Ch7_01 {
    public static void main(String[] args) {
        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> (i + 1) * 300)
                .buffer(1, TimeUnit.SECONDS)

                .subscribe(System.out::println);

        Util.sleep(4000);
    }
}
