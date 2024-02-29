package cn.denghanxi.appium;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

public class AppiumHelper {

    private AppiumHelper() {
    }

    public static void swipeLeft(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.8);
        int endX = (int) (size.width * 0.20);
        int startY = size.height / 2;

        Point source = new Point(startX, startY);
        Point target = new Point(endX, startY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence actions = new Sequence(finger, 1);

        actions.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), source.x, source.y));
        actions.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        actions.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), target.x, target.y));
        actions.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(actions));
    }

    public static void swipeRight(AppiumDriver driver) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.2);
        int endX = (int) (size.width * 0.80);
        int startY = size.height / 2;

        Point source = new Point(startX, startY);
        Point target = new Point(endX, startY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence actions = new Sequence(finger, 1);

        actions.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), source.x, source.y));
        actions.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        actions.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), target.x, target.y));
        actions.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(actions));
    }
}
