package com.adj.ch5;

import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Ch5_20 {
    public static void main(String[] args) {
        Subject<String> subject = AsyncSubject.create();

        subject.subscribe(s->System.out.println("O1: " + s), Throwable::printStackTrace, ()->System.out.println("O1 Done!"));

        subject.onNext("Alpha");
        subject.onNext("Beta");
        subject.onNext("Gamma");
        subject.onComplete();

        subject.subscribe(s->System.out.println("O2: " + s), Throwable::printStackTrace, ()->System.out.println("O2 Done!"));
    }
}
