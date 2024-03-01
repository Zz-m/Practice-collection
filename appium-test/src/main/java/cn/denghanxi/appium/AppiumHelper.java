package cn.denghanxi.appium;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class AppiumHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppiumHelper.class);

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

    public static void swipeUpByElement(AppiumDriver driver, WebElement element) {
        Rectangle rectangle = element.getRect();
        int startX = rectangle.x + rectangle.width / 2;
        int startY = (int) (rectangle.y + rectangle.height * 0.8);
        int endX = rectangle.x + rectangle.width / 2;
        int endY = (int)(rectangle.y + rectangle.height * .02);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence actions = new Sequence(finger, 1);

        actions.addAction(finger.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), startX, startY));
        actions.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        actions.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), endX, endY));
        actions.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(actions));
    }

    public static void clickElement(AppiumDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Rectangle rec = element.getRect();
        int centerX = rec.getX() + (rec.getWidth() / 2);
        int centerY = rec.getY() + (rec.getHeight() / 2);
        Point elementLocation = new Point(centerX, centerY);
        tapPoint(driver, elementLocation);
    }

    public static void tapPoint(AppiumDriver driver,Point point) {
        LOGGER.debug("click x:{}, y:{}", point.x, point.y);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.getX(), point.getY()));
        tap.addAction(finger.createPointerDown(0));
        tap.addAction(finger.createPointerUp(0));
        driver.perform(List.of(tap));
    }
}
