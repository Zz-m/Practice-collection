package com.adj.ch2;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.ResourceObserver;

public class Ch2_31 {
    public static void main(String[] args) {

        Single single = Single.fromCallable(()->{throw new RuntimeException("he");});
        single.subscribe(s->System.out.println("get: " + s), e->System.out.println(e.getClass()));
        Observable source = Observable.just("Hello");
        ResourceObserver rio = new ResourceObserver() {
            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        DisposableObserver dio = new DisposableObserver() {
            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        } ;
        source.subscribeWith(dio);

        Observable<Integer> io = Observable.create(emitter -> {
            for (int i = 0; i < 10 ;i ++) {
                if (emitter.isDisposed()) return;
                emitter.onNext(i);
            }
            emitter.onComplete();
        });
        io.subscribe(dio);
    }
}
