package cn.denghanxi.api;

import cn.denghanxi.AppConstants;
import cn.denghanxi.Singleton;
import cn.denghanxi.model.AndroidDevice;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class AppiumApi {
    private Logger logger = LoggerFactory.getLogger(AppiumApi.class);

    private static final HttpUrl URL_APP_SERVER_LIST_DEVICE = new HttpUrl.Builder()
            .scheme("http")
            .host("127.0.0.1")
            .port(AppConstants.APPIUM_SERVER_PORT)
            .addPathSegment("devices")
            .build();

    private OkHttpClient httpClient = Singleton.serverHttpClient;

    public List<AndroidDevice> queryAllDevices() throws IOException {
        Request request = new Request.Builder().url(URL_APP_SERVER_LIST_DEVICE).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() != 200) {
                throw new IOException("Appium query fail. Code:{}"+ response.code());
            }
            logger.debug("qeury devices: {}",response.body().string());
        }
        return null;
    }
}
