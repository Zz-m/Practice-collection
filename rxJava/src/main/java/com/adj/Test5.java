package com.adj;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class Test5 {

    public static void main(String[] args) {
        Observable<Integer> ob1 = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        });
        Observable<Integer> ob2 = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        });
        Observable<Integer> ob3 = Observable.create(emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        });

        Observable<Integer> obs = Observable.just(1, 2, 3)
                .concatMap(i -> {
                    switch (i) {
                        case 1: return ob1;
                        case 2: return ob2;
                        default:return ob3;
                    }
                });

        obs.subscribe(integer -> {System.out.println("get: " + integer);});
    }
}
