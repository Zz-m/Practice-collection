package com.adj.upwork;

public interface MyAsyncApi {
    Cancellable operation(int param, Callback onSuccess, ErrorCallback onError);
}
