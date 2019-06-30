package com.adj;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello");
        Disposable disposable = Flowable.fromArray(args).subscribe(s -> System.out.println("Hello" + s));
    }
}
