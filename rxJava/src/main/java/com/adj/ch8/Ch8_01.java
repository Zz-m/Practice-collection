package com.adj.ch8;

import com.adj.util.Util;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.Flow;

public class Ch8_01 {
    public static void main(String[] args) {
        CompositeDisposable disposable = new CompositeDisposable();


        Disposable d = Flowable.range(1, 9_999_999)
                .map(MyItem::new)
                .observeOn(Schedulers.io())
                .subscribe(myItem -> {
                    Util.sleep(50);
                    System.out.println("Received MyItem " + myItem.id + " t:" + Thread.currentThread().getName());
                });

        Util.sleep(30000);
    }

    static final class MyItem {
        final int id;

        MyItem(int id) {
            this.id = id;
            System.out.println("Constructing MyItem " + id + " t:" + Thread.currentThread().getName());
        }
    }
}
