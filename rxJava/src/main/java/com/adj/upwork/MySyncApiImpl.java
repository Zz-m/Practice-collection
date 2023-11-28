package com.adj.upwork;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class MySyncApiImpl {

    private final MyAsyncApi api;
    private final AtomicBoolean isWorking = new AtomicBoolean(false);
    private final ReentrantLock workLock = new ReentrantLock();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private Future<?> currentFuture;

    public MySyncApiImpl(MyAsyncApi api) {
        this.api = api;
    }

    /**
     * Runs MyAsyncApi.operation and blocks until it finishes. Throws IllegalStateException if called while operation is running.
     */
    public int operation(int param) throws MyApiException {
        //Please implement this method using this.api

        //lock to prevent call from other threads.
        if (!workLock.tryLock()) {
            throw new IllegalStateException("Another work is running.");
        }

        if (!currentFuture.isDone()) {
            //same thread call 'operation' twice. All throw IllegalStateException as comment of Question.
            throw new IllegalStateException("Another work is running.");
        }
        currentFuture = executorService.submit(() -> {
            try {
                api.operation(param, result -> {
                    //work success.
                }, error -> {
                    //handle error.
                });
                workLock.unlock();
            } catch (Exception e) {
                workLock.unlock();
            }
        });

        return 0;
    }

    /**
     * Cancel most recent operation started with 'operation' method.
     * Return true of operation was cancelled and false otherwise.
     */
    public boolean cancelOperation() {
        //Cancel the running operation.
        if (currentFuture == null) return false;
        return currentFuture.cancel(true);
    }
}
