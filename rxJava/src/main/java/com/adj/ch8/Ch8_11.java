package com.adj.ch8;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch8_11 {
    public static void main(String[] args) {
        Observable<Integer> source = Observable.range(1, 1000);

        source.toFlowable(BackpressureStrategy.BUFFER)
                .onBackpressureBuffer()
                .observeOn(Schedulers.io())
                .onBackpressureLatest()
                .subscribe(System.out::println);

        Util.sleep(1000);
    }
}
