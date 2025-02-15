package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class MainPageObject
{
    private static final String
            READING_LIST_TPL = "//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='{READING_LIST_NAME}']";

    protected AndroidDriver driver;
    public MainPageObject(AndroidDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public boolean waitForElementNotPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    public WebElement waitForElementAndClick(By by, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.sendKeys(value);
        return element;
    }

    public WebElement waitForElementAndClear(By by, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    public void assertElementHasText(By by, String expected_text, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        String actual_text = element.getText();

        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    };

    public List<WebElement> waitForSeveralElementsPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public void swipeUp (int timeOfSwipe) throws InterruptedException {
        Dimension size = driver.manage().window().getSize();
        int start_x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start_x, start_y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), start_x, end_y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));

        Thread.sleep(5000);
    }

    public void swipeUpQuick () throws InterruptedException {
        swipeUp(200);
    }

    public void swipeUpToFindElement (By by, String error_message, int timeOfSwipe) throws InterruptedException {
        while (driver.findElements(by).isEmpty())
        {
            swipeUpQuick();
        }
    }

    public void swipeElementToLeft (By by, String error_message,int max_swipes) throws InterruptedException {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);

        int start_x = 1253;
        int start_y = 1145;
        int end_x = 42;
        int end_y = 1145;


        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start_x, start_y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(200), PointerInput.Origin.viewport(), end_x, end_y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));

        Thread.sleep(5000);
    }

    public int getAmountOfElements (By by) throws InterruptedException {
        List<WebElement> elements = driver.findElements(by);
        return elements.size();

    }

//    public int getAmountOfTitles (By by) {
//        List<WebElement> element = driver.findElements(by);
//        return element.size();
//
//    }

    public void assertElementNotPresent (By by, String error_message) throws InterruptedException {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be present.";
            throw new AssertionError(default_message + " " + error_message);}
    }

    public void assertElementPresent (By by, String error_message) throws InterruptedException {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements == 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present.";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    public String waitForElementAndGetAttribute (By by, String attribute, String error_message,long timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        return element.getDomAttribute(attribute);
    }

    private static String getReadingListXpathByName(String name_of_reading_list)
    {
        return READING_LIST_TPL.replace("{READING_LIST_NAME}", name_of_reading_list);
    }

    public void openReadingListByName(String name_of_reading_list)
    {
        String readingListNameXpath = getReadingListXpathByName(name_of_reading_list);
        this.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_reading_list + "']"),
                "Cannot find created reading list!",
                5);
    }

}
