package cn.denghanxi.adb;

import cn.denghanxi.model.AndroidDevice;
import com.google.common.base.Strings;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdbManager {
    private final Logger logger = LoggerFactory.getLogger(AdbManager.class);

    private static final AdbManager INSTANCE = new AdbManager();

    private AdbManager() {
    }

    public static AdbManager getInstance() {
        return INSTANCE;
    }

    public List<AndroidDevice> getAllDevices() throws IOException {
        List<AndroidDevice> result = new ArrayList<>();

        CommandLine command = CommandLine.parse("adb devices");
        DefaultExecutor executor = DefaultExecutor.builder().get();
        ExecuteWatchdog watchdog = ExecuteWatchdog.builder().setTimeout(Duration.ofSeconds(10)).get();
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        executor.setStreamHandler(new PumpStreamHandler(outputStream));
        int exitValue = executor.execute(command);
        if (exitValue != 0) {
            logger.error("execute adb devices fail with code:{}", exitValue);
            return Collections.emptyList();
        }
        String resultString = outputStream.toString(StandardCharsets.UTF_8);
        resultString.lines()
                .filter(s -> !s.startsWith("*"))
                .skip(1)
                .forEach(s -> {
                    if (!Strings.isNullOrEmpty(s)) {
                        String[] sArray = s.split("\\s+");
                        if (sArray.length < 2) {
                            logger.error("get strange result from 'adb devices' command: [{}], sArray size:{}", s, sArray.length);
                        } else {
                            result.add(new AndroidDevice(sArray[0], sArray[1]));
                        }
                    } else {
                        logger.debug("get empty string from 'adb devices' command");
                    }
                });
        outputStream.close();
        return Collections.unmodifiableList(result);
    }

    public boolean resetApp(String deviceUdid, String packageName) {
        String commandString = "adb -s " + deviceUdid + " shell pm clear " + packageName;
        CommandLine commandLine = CommandLine.parse(commandString);
        DefaultExecutor executor = DefaultExecutor.builder().get();
        ExecuteWatchdog watchdog = ExecuteWatchdog.builder().setTimeout(Duration.ofSeconds(10)).get();
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        try {
            return executor.execute(commandLine) == 0;
        } catch (IOException e) {
            logger.error("resetApp fail on device:[{}]", deviceUdid, e);
            return false;
        }
    }

    public boolean startAppByPackage(String deviceUdid, String packageName) {
        String commandString = "adb -s " + deviceUdid + " shell monkey -p " + packageName + " -c android.intent.category.LAUNCHER 1";
        CommandLine commandLine = CommandLine.parse(commandString);
        DefaultExecutor executor = DefaultExecutor.builder().get();
        ExecuteWatchdog watchdog = ExecuteWatchdog.builder().setTimeout(Duration.ofSeconds(10)).get();
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        try {
            return executor.execute(commandLine) == 0;
        } catch (IOException e) {
            logger.error("resetApp fail on device:[{}]", deviceUdid, e);
            return false;
        }
    }
}
