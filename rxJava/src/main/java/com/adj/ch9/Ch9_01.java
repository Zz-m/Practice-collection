package com.adj.ch9;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Ch9_01 {
    public static void main(String[] args) {
        Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
                .collect(ArrayList<String>::new, List::add)
                .map(LinkedList::new)
                .subscribe(System.out::println);
        Observable.range(1, 10)
                .collect(ArrayList<Integer>::new, List::add)
                .map(LinkedList::new)
                .subscribe(System.out::println);


    }

}
