package com.adj;


import io.reactivex.rxjava3.core.Observable;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
        Observable.create(s->{
            s.onNext("Hello World!");
            s.onComplete();
        }).subscribe(System.out::println);

        Observable<Integer> o1 = Observable.just(1, 2, 3);

        Observable<Boolean> o2 = o1.concatMap(i -> {
            if (i == 1) {
                return Observable.just(true);
            } if (i == 3) {
                return Observable.just(true);
            } else {
                return Observable.empty();
            }
        });

        o2.subscribe(System.out::println);

    }

}
