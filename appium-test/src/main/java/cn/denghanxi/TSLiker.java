package cn.denghanxi;

import cn.denghanxi.api.AppiumApi;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.Slf4jLogMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.function.BiConsumer;

public class TSLiker {
    private final Logger logger = LoggerFactory.getLogger(TSLiker.class);
    private AppiumApi api = new AppiumApi();

    public void start() {
        logger.debug("begin");
        AppiumDriverLocalService localService = null;
        try {
            localService = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingPort(AppConstants.APPIUM_SERVER_PORT));

            localService.enableDefaultSlf4jLoggingOfOutputData();
            localService.start();
            logger.debug("localService.isRunning:{}", localService.isRunning());


            try {
                Thread.sleep(5000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            api.queryAllDevices();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (localService != null) {
                localService.stop();
                localService.close();
            }
        }
    }

    private void setupDevice() {

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAppPackage("com.truthsocial.android.app")
                .setNoReset(false)
                .autoGrantPermissions()
                .setAutomationName("UiAutomator2")
                .setAppActivity("com.truthsocial.app.features.login.LoginActivity")
                .ensureWebviewsHavePages()
                .nativeWebScreenshot()
                .setNewCommandTimeout(Duration.ofSeconds(10))
                .setUdid("123");
        try {
            AndroidDriver driver = new AndroidDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:" + AppConstants.APPIUM_SERVER_PORT), options
            );
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        logger.debug("create driver success.");
    }
}
