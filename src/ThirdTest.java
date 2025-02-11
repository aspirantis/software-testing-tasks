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


public class ThirdTest {

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

        waitForElementPresent(
                By.xpath("//android.widget.TextView[contains(@text, 'Search')]"),
                "Cannot find Search!'",
                10);

    }

    private WebElement waitForElementPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
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

    private void assertElementHasText(By by, String expected_text, String error_message, int timeOutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, timeOutInSeconds);
        String actual_text = element.getDomAttribute("text");

        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text);
    };
}
