package com.adj.ch5;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import io.reactivex.rxjava3.subjects.UnicastSubject;

import java.util.concurrent.TimeUnit;

public class Ch5_21 {
    public static void main(String[] args) throws InterruptedException {
        Subject<String> subject = UnicastSubject.create();
        Subject<String> subject2 = ReplaySubject.create();

        Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(l -> ((l + 1) * 300) + " milliseconds")
                .subscribe(subject);

        Thread.sleep(2000);
        subject.subscribe(s -> System.out.println("O1: " + s));
        Thread.sleep(2000);
    }
}
