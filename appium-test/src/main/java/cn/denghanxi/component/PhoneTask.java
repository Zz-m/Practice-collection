package cn.denghanxi.component;

import cn.denghanxi.AppConstants;
import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.model.AndroidDevice;
import cn.denghanxi.model.TSAccount;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Queue;

public class PhoneTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(PhoneTask.class);
    private final AdbManager adbManager = AdbManager.getInstance();

    private final AndroidDevice device;
    private final Queue<TSAccount> accountQueue;
    private final List<String> postList;
    private final ProgressCallback progressCallback;

    public PhoneTask(AndroidDevice device, Queue<TSAccount> accountQueue, List<String> postList, ProgressCallback progressCallback) {
        if (!device.isReady()) throw new RuntimeException("device: " + device.udid() + " is not ready.");
        this.device = device;
        this.accountQueue = accountQueue;
        this.postList = postList;
        this.progressCallback = progressCallback;
    }

    @Override
    public void run() {
        logger.debug("tast start for device: [{}]", device.udid());
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
        }
        logger.debug("create driver finish for device:{}", device.udid());
        TSAccount account = accountQueue.poll();
        while (account != null) {
            resetApp(driver);
            tsLogin(driver, account);
            for (String post : postList) {
                likerPost(driver, post);
            }

            account = accountQueue.poll();
            progressCallback.completeOneAccount();
        }


    }

    private UiAutomator2Options getOptionByDevice(AndroidDevice device) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAppPackage(AppConstants.APP_PACKAGE)
                .setNoReset(false)
                .autoGrantPermissions()
                .setAutomationName("UiAutomator2")
                .setAppActivity("com.truthsocial.app.features.login.LoginActivity")
                .ensureWebviewsHavePages()
                .nativeWebScreenshot()
                .setNewCommandTimeout(Duration.ofSeconds(10))
                .setUdid(device.udid());
        return options;
    }

    private boolean resetApp(AndroidDriver driver) {
        logger.debug("try to reset APP...");
        //close app
//        driver.terminateApp(AppConstants.APP_PACKAGE);
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        logger.debug("terminate app success");
        //clear app
        return adbManager.resetApp(device.udid(), AppConstants.APP_PACKAGE);
    }

    private void tsLogin(AndroidDriver driver, TSAccount account) {
        driver.activateApp(AppConstants.APP_PACKAGE);
        logger.debug("launch app success.");
        WebDriverWait gotoLoginButtonWait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        gotoLoginButtonWait.until().wait();
    }

    private void likerPost(AndroidDriver driver, String post) {

    }
}
