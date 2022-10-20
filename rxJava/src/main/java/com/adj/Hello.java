package com.adj;


import io.reactivex.rxjava3.core.Observable;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
        Observable.create(s->{
            s.onNext("Hello World!");
            s.onComplete();
        }).subscribe(System.out::println);

    }

}
