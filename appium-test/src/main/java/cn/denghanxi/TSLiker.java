package cn.denghanxi;

import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.api.AppiumApi;
import cn.denghanxi.appium.AppiumHelper;
import cn.denghanxi.appium.AppiumServerManager;
import cn.denghanxi.model.AndroidDevice;
import cn.denghanxi.model.TSAccount;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TSLiker {
    private final Logger logger = LoggerFactory.getLogger(TSLiker.class);
    private AppiumApi api = new AppiumApi();
    private AppiumServerManager serverManager = AppiumServerManager.getInstance();

    private final ConcurrentLinkedQueue<TSAccount> accountQueue = new ConcurrentLinkedQueue<>();
    private List<String> postList = null;
    private List<AndroidDevice> deviceList = null;

    public void start() {
        logger.debug("begin");

        prepareAllData();

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

    public void test() {
        boolean prepareResult = prepare();
        logger.debug("Prepare result:{}", prepareResult);
        if (!prepareResult) {
            return;
        }
        testProgress();
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

    private boolean prepare() {
        while (true) {
            if (!prepareAllData()) {
                return false;
            }
            report();
            System.out.print("Press 'y' to start, press 'r' to rescan devices, press any keys to stop:");
            Scanner scanner = new Scanner(System.in);
            String in = scanner.nextLine();
            if ("Y".equals(in) || "y".equals(in)) {
                return true;
            }
            if ("R".equals(in) || "r".equals(in)) {
                continue;
            }
            return false;
        }
    }

    private boolean prepareAllData() {
        //accounts
        Path accountPath = Paths.get("accounts.csv");
        File accountFile = accountPath.toFile();

        if (!accountFile.exists()) {
            logger.debug("Accounts file not found from: {}", accountPath);
            return false;
        }

        if (accountFile.length() == 0) {
            logger.debug("Accounts file is empty.");
            return false;
        }

        String[] HEADERS = {"Username", "Password"};

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        accountQueue.clear();
        try {
            Iterable<CSVRecord> accounts = csvFormat.parse(new FileReader(accountFile));
            for (CSVRecord record : accounts) {
                accountQueue.add(new TSAccount(record.get(0), record.get(1)));
            }
        } catch (FileNotFoundException e) {
            logger.error("Accounts file not found.", e);
            return false;
        } catch (IOException e) {
            logger.error("Read Accounts file fail.", e);
            return false;
        }

        //posts
        Path postsPath = Paths.get("posts.txt");
        File postsFile = postsPath.toFile();

        if (!postsFile.exists()) {
            logger.debug("Posts file not found from: {}", postsPath);
            return false;
        }

        if (postsFile.length() == 0) {
            logger.debug("Posts file is empty.");
            return false;
        }
        try {
            BufferedReader postsReader = new BufferedReader(new FileReader(postsFile));
            List<String> posts = new ArrayList<>();
            String line = postsReader.readLine().trim();
            while (line != null) {
                if (!line.isEmpty()) {
                    posts.add(line);
                }
                line = postsReader.readLine();
            }
            if (posts.isEmpty()) {
                logger.error("Read posts file get 0 post.");
                return false;
            }
            postList = Collections.unmodifiableList(posts);
        } catch (FileNotFoundException e) {
            logger.error("Posts file not found.", e);
            return false;
        } catch (IOException e) {
            logger.error("Read posts file fail.", e);
            return false;
        }

        //devices
        try {
            deviceList = AdbManager.getInstance().getAllDevices();
            if (deviceList.isEmpty()) {
                logger.error("Get zero device.");
                return false;
            }
        } catch (IOException e) {
            logger.error("get devices fail.", e);
            return false;
        }

        return true;
    }

    private void report() {
        logger.debug("******************************************************");
        if (postList == null || deviceList == null) {
            logger.error("Can not create report.");
            return;
        }
        logger.debug("We have {} accounts to like {} posts.", accountQueue.size(), postList.size());
        List<AndroidDevice> readyList = new ArrayList<>();
        List<AndroidDevice> notReadyList = new ArrayList<>();
        for (AndroidDevice device : deviceList) {
            if (device.isReady()) {
                readyList.add(device);
            } else {
                notReadyList.add(device);
            }
        }
        logger.debug("{} devices is ready.", readyList.size());
        StringBuilder readyNames = new StringBuilder();
        for (AndroidDevice device : readyList) {
            readyNames.append(device.udid()).append(" ");
        }
        logger.debug(readyNames.toString());
        if (!notReadyList.isEmpty()) {
            logger.warn("{} device is not ready.", notReadyList.size());
            StringBuilder notReadyNames = new StringBuilder();
            for (AndroidDevice device : notReadyList) {
                notReadyNames.append(device.udid()).append(" ");
            }
            logger.warn(notReadyNames.toString());
        }
        logger.debug("******************************************************");
    }


    //=====================================
    private void testProgress(){

    }
}
