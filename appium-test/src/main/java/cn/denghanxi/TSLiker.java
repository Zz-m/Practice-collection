package cn.denghanxi;

import cn.denghanxi.api.AppiumApi;
import cn.denghanxi.appium.AppiumHelper;
import cn.denghanxi.appium.AppiumServerManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class TSLiker {
    private final Logger logger = LoggerFactory.getLogger(TSLiker.class);
    private AppiumApi api = new AppiumApi();
    private AppiumServerManager serverManager = AppiumServerManager.getInstance();

    public void start() {
        logger.debug("begin");
        AppiumDriverLocalService localService = serverManager.getOrCreateLocalService();
        logger.debug("localService.isRunning:{}", localService.isRunning());

        printPath();


        Thread t1 = new Thread(() -> {
            logger.debug("Thread 1 start");
            this.testDevice1();
        });
        Thread t2 = new Thread(this::testDevice2);

        t1.start();
        t2.start();


        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//
//            logger.debug("get response:{}", response.getValue());
//


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

    private void testDevice1() {

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
                .setUdid("kn6dsgaepzk7pnce");
        try {
            AndroidDriver driver = new AndroidDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:" + AppConstants.APPIUM_SERVER_PORT), options
            );
            driver.pressKey(new KeyEvent(AndroidKey.HOME));

            while (true) {
                try {
                    AppiumHelper.swipeLeft(driver);
                    Thread.sleep(2000);
                    AppiumHelper.swipeRight(driver);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    logger.debug("InterruptedException");
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private void testDevice2() {

        logger.debug("testDevice2 start");
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
                .setUdid("49c8aa83");
        try {
            AndroidDriver driver = new AndroidDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:" + AppConstants.APPIUM_SERVER_PORT), options
            );

            driver.pressKey(new KeyEvent(AndroidKey.HOME));


            while (true) {

                AppiumHelper.swipeLeft(driver);
                AppiumHelper.swipeRight(driver);

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    private void printPath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        logger.debug("current path:{}", s);
    }
}
