package cn.denghanxi.component;

import cn.denghanxi.AppConstants;
import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.appium.AppiumHelper;
import cn.denghanxi.model.AndroidDevice;
import cn.denghanxi.model.TSAccount;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
            try {
                boolean resetSuccess = resetApp(driver);
                if (!resetSuccess) {
                    logger.warn("Reset application fail. Retry...");
                    continue;
                }
                tsLogin(driver, account);
                try {
                    handlePermissionNotification(driver);
                } catch (Exception e) {
                    logger.error("Handle permission error:", e);
                    return;
                }
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.view.View[@content-desc='User Avatar']")));
                logger.debug("already in main activity.");

                for (String post : postList) {
                    likerPost(driver, post);
                }
                //wait to sync to server.
                Thread.sleep(4000);
            } catch (TimeoutException timeoutException) {
                logger.warn("Some operate on device [{}] timeout. Handle next account.", device.udid());
            } catch (Exception e) {
                logger.error("Unhandled exception happen.", e);
            }finally {
                progressCallback.completeOneAccount();
            }

            account = accountQueue.poll();
        }


    }

    private UiAutomator2Options getOptionByDevice(AndroidDevice device) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setAppPackage(AppConstants.APP_PACKAGE)
                .setNoReset(false)
                .setAutoGrantPermissions(true)
                .setAutomationName("UiAutomator2")
                .setAppActivity("com.truthsocial.app.features.login.LoginActivity")
                .ensureWebviewsHavePages()
                .nativeWebScreenshot()
                .setNewCommandTimeout(Duration.ofSeconds(10))
                .setUdid(device.udid());
        return options;
    }

    private boolean resetApp(AndroidDriver driver) throws InterruptedException {
        logger.debug("try to reset APP...");
        //close app
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        logger.debug("press home success");
        //clear app
        boolean result =adbManager.resetApp(device.udid(), AppConstants.APP_PACKAGE);
        Thread.sleep(3000);
        return result;
    }

    private void tsLogin(AndroidDriver driver, TSAccount account) {
        driver.activateApp(AppConstants.APP_PACKAGE);
        logger.debug("launch app success.");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement gotoLoginButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text=\"Already have an account? Sign In\"]")));
        logger.debug("find goto login button success!");
        gotoLoginButton.click();
        //wait user input
        WebElement userNameInput = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ScrollView/android.widget.EditText[1]")));
        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.ScrollView/android.widget.EditText[2]")));

        userNameInput.sendKeys(account.userName());
        passwordInput.sendKeys(account.password());

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.xpath("//android.widget.TextView[@text='LOGIN']")));
        loginButton.click();
    }

    private void likerPost(AndroidDriver driver, String post) {
        driver.get(post);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //wait page loaded.
        wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.TextView[@text='Truth Details']")));

        WebElement scrollElement = null;

        try {
            scrollElement = driver.findElement(AppiumBy.xpath("(//android.widget.FrameLayout[@resource-id=\"com.truthsocial.android.app:id/nav_host_fragment_feed\"])[2]/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View"));
        } catch (NoSuchElementException e) {
            logger.error("fail to find scroll view.", e);
            return;
        }
        int counter = 0;

        while (counter < 5) {
            try {
                WebElement likeButton = driver.findElement(AppiumBy.xpath("//android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.widget.Button[3]"));

                logger.debug("find like button.");
                wait.until(ExpectedConditions.elementToBeClickable(likeButton));
//                likeButton.click();
                AppiumHelper.clickElement(driver, likeButton);
                logger.debug("like success!");
                break;
            } catch (NoSuchElementException e) {
                logger.debug("not find like button, scroll...");
                AppiumHelper.swipeUpByElement(driver, scrollElement);
            } finally {
                counter ++;
            }
        }

    }

    private void handlePermissionNotification(AndroidDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("//android.widget.LinearLayout[@resource-id=\"com.lbe.security.miui:id/parentPanel\"]")));
            try {
                WebElement approveButton = driver.findElement(AppiumBy.xpath(".//android.widget.Button[@text='Allow']"));
                wait.until(ExpectedConditions.elementToBeClickable(approveButton));
                approveButton.click();
            } catch (Exception e) {
                logger.debug("not find permission allow button with text 'Allow'");
            }

            try {
                WebElement approveButton2 = driver.findElement(AppiumBy.xpath("//android.widget.Button[@text='Allow all the time']"));
                wait.until(ExpectedConditions.elementToBeClickable(approveButton2));
                approveButton2.click();
            } catch (Exception e) {
                logger.debug("not find permission allow button with text 'Allow all the time'");
            }
        } catch (TimeoutException e) {
            logger.debug("Wait permission notification fail.");
        }

    }
}
