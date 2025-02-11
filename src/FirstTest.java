import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "pixel9proxl");
        capabilities.setCapability("appium:platformVersion", "13.0");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:app", "D:\\aspirantis\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");
        capabilities.setCapability("appium:fullReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

//    @Test
//    public void firstTest() throws InterruptedException
//    {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
//                "Cannot find SKIP button",
//                5);
//
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia' input",
//                5);
//
//        waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Cannot find search input",
//                5);
//
//        Thread.sleep(2000);
//
//        waitForElementPresent(
//                By.xpath("//android.widget.TextView[contains(@text, 'Java (programming language)')]"),
//                "Cannot find 'Java (programming language)'",
//                10);
//
//        Thread.sleep(5000);
//
//        System.out.println("First test run");
//    }
//
//    @Test
//    public void testCancelSearch()
//    {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
//                "Cannot find SKIP button!",
//                5);
//
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia' input",
//                5);
//
//        waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Cannot find 'Java'!",
//                5);
//
//        waitForElementAndClear(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Cannot find search field",
//                5);
//
//        waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Can not find 'Java'!",
//                5);
//
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Cannot find X to cancel search!",
//                5);
//
//        waitForElementNotPresent(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "X is still present on the page!",
//                5);
//    }
//
//    @Test
//    public void testCompareArticleTitle()
//    {
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
//                "Cannot find SKIP button!",
//                5);
//
//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_container"),
//                "Cannot find 'Search Wikipedia' input!",
//                5);
//
//        waitForElementAndSendKeys(
//                By.id("org.wikipedia:id/search_src_text"),
//                "Java",
//                "Can not find 'Java'!",
//                5);
//
//        WebElement title_element = waitForElementPresent(
//                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Java (programming language)']"),
//                "Cannot find article title!",
//                5);
//
//        String article_title = title_element.getDomAttribute("text");
//
//        Assert.assertEquals(
//                "We see unexpected title!",
//                "Java (programming language)",
//                article_title
//        );
//    }

    @Test
    public void testAssertElementHasText() throws InterruptedException {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }

    @Test
    public void testSearchPresence() throws InterruptedException {
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }

    private WebElement waitForElementPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 10);
    }

    private WebElement waitForElementAndClick(By by, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message, int timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        String actual_text = element.getText();

        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    };
}