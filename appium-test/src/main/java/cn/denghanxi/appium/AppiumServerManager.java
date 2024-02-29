package cn.denghanxi.appium;

import cn.denghanxi.AppConstants;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;

public class AppiumServerManager implements Closeable {
    private final Logger logger = LoggerFactory.getLogger(AppiumServerManager.class);

    public static AppiumServerManager INSTANCE = new AppiumServerManager();

    private AppiumDriverLocalService service;

    public static AppiumServerManager getInstance() {
        return INSTANCE;
    }

    private AppiumServerManager(){
        Thread shutdownHook = new Thread(() -> {
            try {
                this.close();
            } catch (IOException e) {
                logger.error("shutdown appium server with error", e);
            }
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    public AppiumDriverLocalService getOrCreateLocalService() {
        if (service == null || !service.isRunning()) {
            service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                    .usingPort(AppConstants.APPIUM_SERVER_PORT)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "warn"));
            service.enableDefaultSlf4jLoggingOfOutputData();
            service.start();
        }
        return service;
    }

    @Override
    public void close() throws IOException {
        if (service != null) {
            service.stop();
            service.close();
            service = null;
            logger.debug("Appium service shutdown graceful.");
        } else {
            logger.debug("No appium service to shutdown.");
        }
    }
}
