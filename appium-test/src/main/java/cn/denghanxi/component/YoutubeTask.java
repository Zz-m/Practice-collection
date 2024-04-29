package cn.denghanxi.component;

import cn.denghanxi.AppConstants;
import cn.denghanxi.model.AndroidDevice;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.options.BaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class YoutubeTask implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(YoutubeTask.class);

    private final AndroidDevice androidDevice;

    public YoutubeTask(AndroidDevice androidDevice) {
        this.androidDevice = androidDevice;
    }

    @Override
    public void run() {
        logger.debug("task start for device: [{}]", androidDevice.udid());
        UiAutomator2Options options = getOptionByDevice(androidDevice);
        AndroidDriver driver = null;
        logger.debug("create driver for device:{}", androidDevice.udid());
        try {
            driver = new AndroidDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:" + AppConstants.APPIUM_SERVER_PORT), options);
        } catch (MalformedURLException e) {
            logger.error("Create driver fail.", e);
            throw new RuntimeException(e);
        }catch (Exception e) {
            logger.debug("unhandled exception:", e);
        }
        logger.debug("create driver finish for device:{}", androidDevice.udid());

        logger.debug("start youtube");

        driver.activateApp(AppConstants.YOU_TUBE_APP_PACKAGE);

    }

    private UiAutomator2Options getOptionByDevice(AndroidDevice device) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAppPackage(AppConstants.YOU_TUBE_APP_PACKAGE)
                .setNoReset(false)
                .setAutoGrantPermissions(true)
                .setAutomationName("UiAutomator2")
//                .setAppActivity("com.truthsocial.app.features.login.LoginActivity")
                .ensureWebviewsHavePages()
                .nativeWebScreenshot()
                .setNewCommandTimeout(Duration.ofSeconds(10))
                .setUdid(device.udid());
        return options;
    }
}
