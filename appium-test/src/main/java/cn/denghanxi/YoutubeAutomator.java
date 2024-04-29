package cn.denghanxi;

import cn.denghanxi.adb.AdbManager;
import cn.denghanxi.appium.AppiumServerManager;
import cn.denghanxi.component.YoutubeTask;
import cn.denghanxi.model.AndroidDevice;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class YoutubeAutomator {
    private final Logger logger = LoggerFactory.getLogger(YoutubeAutomator.class);
    private final AppiumServerManager serverManager = AppiumServerManager.getInstance();
    private final ExecutorService taskExecutor = Executors.newFixedThreadPool(1);

    private List<AndroidDevice> deviceList = null;

    public void start(){
        logger.debug("start");

        boolean prepareResult = prepare();
        if (!prepareResult) {
            return;
        }

        AppiumDriverLocalService localService = serverManager.getOrCreateLocalService();
        logger.info("localService.isRunning:{}", localService.isRunning());
        if (!localService.isRunning()) {
            logger.error("Start Appium localServer fail. stop.");
            return;
        }

        logger.debug("deviceList size:{}", deviceList.size());
        for (AndroidDevice device : deviceList) {
            if (device.isReady()) {
                taskExecutor.submit(new YoutubeTask(device));
            }
        }


        taskExecutor.shutdown();

        try {
//            Thread.sleep(1000000);
            boolean flag = taskExecutor.awaitTermination(1, TimeUnit.HOURS);
            if (flag) {
                logger.debug("task success");
            } else {
                logger.debug("task use too much time");
            }
        } catch (InterruptedException e) {
            logger.error("Interrupted", e);
        }
    }

    private boolean prepare() {
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
}

