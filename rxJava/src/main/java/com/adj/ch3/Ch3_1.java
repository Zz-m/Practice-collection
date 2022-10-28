package com.adj.ch3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Ch3_1 {
    private static final CompositeDisposable disposables = new CompositeDisposable();

    public static void main(String[] args) {
        Disposable d = Observable.just(1, 2, 3, 6, 77, 12, 4 ,16)
                .reduce(true, (r, in) -> r && in < 5)
                .doOnSubscribe(disposables::add)
                .subscribe(System.out::println);
        BehaviorSubject<String> bs = BehaviorSubject.create();

        NumberFormat numberFormat = new DecimalFormat("#0.000");

        System.out.println(numberFormat.format(0.0201122));

    }
}
