package cn.denghanxi.zhang;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 邓晗熙 on 2019/12/9
 */
public class App {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private void get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", "preurl=/model/GetHits.aspx?action=count&f=0&t=1&modelid=1&id=4182&anticache=703&_=1575899568611;captchaKey=2130acb765;captchaExpire=1575903028;cookie_captchaKey=not;cookie_captchaExpire=1575902330")
//                .header("preurl", "http://www.cbyfy2.com/model/GetHits.aspx?action=count&f=0&t=1&modelid=1&id=4182&anticache=703&_=1575899568611")
//                .header("cookie_captchaKey", "notNull")
//                .header("cookie_captchaExpire", "123")
                .header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

    public static void main(String[] args) throws IOException {
        App app = new App();

//        System.out.println(app.run("http://www.cbyfy2.com/v-1-4182.aspx"));

        ExecutorService executor = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executor.submit(() -> {
                Random random = new Random();

// generate a random integer from 0 to 899, then add 100
                try {
                    for (int j = 0; j < 1000000; j++) {
                        int cache = random.nextInt(900) + 100;
                        System.out.println("cache: " + cache);
                        app.get("http://www.cbyfy2.com/model/GetHits.aspx?action=count&f=0&t=1&modelid=1&id=4182&anticache=189&_=1575901510661");
//                        app.get("http://www.cbyfy2.com/v-1-4182.aspx");
//                        Thread.sleep(1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}
