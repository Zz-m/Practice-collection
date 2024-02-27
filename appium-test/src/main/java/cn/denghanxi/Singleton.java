package cn.denghanxi;

import okhttp3.OkHttpClient;

import java.time.Duration;

public class Singleton {
    private Singleton(){}

    public static final OkHttpClient serverHttpClient = new OkHttpClient.Builder().callTimeout(Duration.ofSeconds(10)).build();
}
