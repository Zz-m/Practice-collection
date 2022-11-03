package com.adj.ch8;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Ch8_10 {
    public static void main(String[] args) {
        Flowable<Integer> source = Flowable.create(emitter -> {
            for (int i = 0; i < 1000; i++) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(i);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

        source.observeOn(Schedulers.io())
                .subscribe(System.out::println);

        Util.sleep(1000);
    }
}
