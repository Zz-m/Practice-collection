package cn.denghanxi.component;

import cn.denghanxi.AppConstants;
import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.model.AndroidDevice;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.options.BaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class YoutubeTask implements Runnable{
    private final Logger logger = LoggerFactory.getLogger(YoutubeTask.class);

    private final AndroidDevice device;

    public YoutubeTask(AndroidDevice androidDevice) {
        this.device = androidDevice;
    }

    @Override
    public void run() {
        logger.debug("task start for device: [{}]", device.udid());
        UiAutomator2Options options = getOptionByDevice(device);
        AndroidDriver driver = null;
        logger.debug("create driver for device:{}", device.udid());
        try {
            driver = new AndroidDriver(
                    // The default URL in Appium 1 is http://127.0.0.1:4723/wd/hub
                    new URL("http://127.0.0.1:" + AppConstants.APPIUM_SERVER_PORT), options);
        } catch (MalformedURLException e) {
            logger.error("Create driver fail.", e);
            throw new RuntimeException(e);
        }catch (Exception e) {
            logger.debug("unhandled exception:", e);
            return;
        }
        logger.debug("create driver finish for device:{}", device.udid());

        logger.debug("start youtube");

        try {

            AdbManager.getInstance().startAppByPackage(device.udid(), AppConstants.YOU_TUBE_APP_PACKAGE);

            Thread.sleep(3000);

            logger.debug("start finish");
//            driver.terminateApp(AppConstants.YOU_TUBE_APP_PACKAGE);
        } catch (Exception e) {
//            logger.error("unHandled exception", e);
        }


    }

    private UiAutomator2Options getOptionByDevice(AndroidDevice device) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAppPackage(AppConstants.YOU_TUBE_APP_PACKAGE)
                .setNoReset(false)
                .setAutoGrantPermissions(true)
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
                .setAppActivity("com.google.android.youtube.HomeActivity")
                .ensureWebviewsHavePages()
                .nativeWebScreenshot()
                .setNewCommandTimeout(Duration.ofSeconds(10))
                .setUdid(device.udid());
        return options;
    }
}
