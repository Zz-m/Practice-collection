package cn.denghanxi;

import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.appium.AppiumServerManager;
import cn.denghanxi.component.PhoneTask;
import cn.denghanxi.model.AndroidDevice;
import cn.denghanxi.model.TSAccount;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import me.tongfei.progressbar.ProgressBar;
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
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TSLiker {
    private final Logger logger = LoggerFactory.getLogger(TSLiker.class);
    private final ExecutorService taskExecutor = Executors.newCachedThreadPool();
    private final AppiumServerManager serverManager = AppiumServerManager.getInstance();

    private final ConcurrentLinkedQueue<TSAccount> accountQueue = new ConcurrentLinkedQueue<>();
    private final Map<String, Future<?>> taskMap = new HashMap<>();
    private List<String> postList = null;
    private List<AndroidDevice> deviceList = null;

    public void start() {
        logger.info("start");

        boolean prepareSuccess = prepare();
        if (!prepareSuccess) {
            return;
        }

        logger.info("starting local server...");
        AppiumDriverLocalService localService = serverManager.getOrCreateLocalService();
        logger.info("localService.isRunning:{}", localService.isRunning());
        if (!localService.isRunning()) {
            logger.error("Start Appium localServer fail. stop.");
            return;
        }

        int deviceStarted = 0;

        try (ProgressBar progressBar = new ProgressBar("TSLiker", accountQueue.size())) {
            for (AndroidDevice device : deviceList) {
                if (device.isReady()) {
                    deviceStarted++;
                    Future<?> f = taskExecutor.submit(new PhoneTask(device, accountQueue, postList, progressBar::step));
                    taskMap.put(device.udid(), f);
                }
            }
            logger.debug("start task count:{}", deviceStarted);


            while (!accountQueue.isEmpty()) {
                try {
                    Thread.sleep(3000);
                    try {
                        List<AndroidDevice> updateDeviceList = AdbManager.getInstance().getAllDevices();
                        List<AndroidDevice> readyDeviceList = new ArrayList<>();
                        Set<String> readyDeviceIdSet = new HashSet<>();
                        for (AndroidDevice device : updateDeviceList) {
                            if (device.isReady()) {
                                readyDeviceList.add(device);
                                readyDeviceIdSet.add(device.udid());
                            }
                        }
                        Set<String> currentRunningId = Set.copyOf(taskMap.keySet());
                        //stop unplug phone task
                        for (String id : currentRunningId) {
                            if (!readyDeviceIdSet.contains(id)) {
                                logger.warn("Device:{} disconnected! Remove the task.", id);
                                Future<?> f = taskMap.get(id);
                                f.cancel(true);
                                taskMap.remove(id);
                            }
                        }

                        //create new plugin phone task
                        currentRunningId = Set.copyOf(taskMap.keySet());
                        for (AndroidDevice device : readyDeviceList) {
                            if (!currentRunningId.contains(device.udid())) {
                                logger.info("Find new device:{} connected. Start new task.", device.udid());
                                Future<?> f = taskExecutor.submit(new PhoneTask(device, accountQueue, postList, progressBar::step));
                                taskMap.put(device.udid(), f);
                            }
                        }

                    } catch (IOException e) {
                        logger.error("get devices fail.", e);
                    }
                } catch (InterruptedException e) {
                    logger.warn("Main thread interrupted.", e);
                }
            }
            taskExecutor.shutdown();
            while (true) {
                boolean allComplete = true;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.warn("interrupted");
                }
                for (Future<?> f : taskMap.values()) {
                    if (!f.isDone()) allComplete = false;
                }
                if (allComplete) break;
            }
        }
        logger.info("Finished.");
        taskExecutor.shutdownNow();

    }

    public void test() {
//        boolean prepareResult = prepare();
//        logger.debug("Prepare result:{}", prepareResult);
//        if (!prepareResult) {
//            return;
//        }
//        testProgress();
//        testPrintPath();
        testLocalServer();
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

    private void testPrintPath() {
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
            logger.error("Accounts file not found from: {}", accountPath.toAbsolutePath());
            return false;
        }

        if (accountFile.length() == 0) {
            logger.error("Accounts file is empty.");
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
            logger.error("Posts file not found from: {}", postsPath.toAbsolutePath());
            return false;
        }

        if (postsFile.length() == 0) {
            logger.error("Posts file is empty.");
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
        System.out.println("******************************************************");
        if (postList == null || deviceList == null) {
            logger.error("Can not create report.");
            return;
        }
        System.out.println("We have " + accountQueue.size() + " accounts to like " +postList.size()+ " posts." );
        List<AndroidDevice> readyList = new ArrayList<>();
        List<AndroidDevice> notReadyList = new ArrayList<>();
        for (AndroidDevice device : deviceList) {
            if (device.isReady()) {
                readyList.add(device);
            } else {
                notReadyList.add(device);
            }
        }
        System.out.println(readyList.size() + " devices is ready." );
        StringBuilder readyNames = new StringBuilder();
        readyNames.append("Ready devices: ");
        for (AndroidDevice device : readyList) {
            readyNames.append(device.udid()).append(" ");
        }
        System.out.println(readyNames);
        if (!notReadyList.isEmpty()) {
            System.out.println(notReadyList.size() + " device is not ready." );
            StringBuilder notReadyNames = new StringBuilder();
            notReadyNames.append("Not ready devices: ");
            for (AndroidDevice device : notReadyList) {
                notReadyNames.append(device.udid()).append(" ");
            }
            System.out.println(notReadyNames);
        }
        System.out.println("******************************************************");
    }


    //=====================================
    private void testProgress() {
        ConcurrentLinkedQueue<TSAccount> testQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100; i++) {
            testQueue.add(new TSAccount("asd", "asd"));
        }
        int end = testQueue.size();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (testQueue.poll() != null) {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }
        try (ProgressBar pb = new ProgressBar("TsLiker", end)) {
            while (!testQueue.isEmpty()) {
                pb.stepTo(end - testQueue.size());
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            logger.error("progress interrupted", e);
        }
    }

    private void testLocalServer() {
        logger.info("starting local server...");
        AppiumDriverLocalService localService = serverManager.getOrCreateLocalService();
        logger.debug("localService.isRunning:{}", localService.isRunning());

        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
