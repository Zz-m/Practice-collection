package com.adj.proxy;

public class HelloServiceImpl implements HelloService {

    private int count;

    public String getHelloWord() {
        count++;
        System.out.println("HelloServiceImpl.getHelloWord been called.");
        return "Hello buddy No." + count;
    }
}
