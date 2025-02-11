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
import java.util.List;


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
    public void assertElementHasText() throws InterruptedException
    {
        String searchWord = "Java";

        // Пропустить онбординг
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        // Кликаем по найденному слову
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        // Проверяем, присутствует ли ожидаемый текст
        assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }

    @Test
    public void testTextPresenceAndCancelSearch() throws InterruptedException
    {
        String searchWord = "Python";

        // Пропустить онбординг
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Python",
                "Can not find 'Python'!",
                5);

        // Проверка того, что в результатах поиска найдено несколько статей. Специально ищем по специальному, особому слову в заглавии статьи
        List<WebElement> searchResults = waitForSeveralElementsPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, 'Pythonidae')]"),
                "Cannot find several pro-Python mentions in search results!",
                5);

        // Проверка того, что количество результатов больше двух
        Assert.assertTrue(
                "Less than 2 search results found!",
                searchResults.size() > 1);

        // Сброс результатов поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel search button!",
                5);

        // Проверка того, что результаты поиска исчезли
        boolean resultsAreGone = waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search results are still visible after canceling search!",
                5
        );

        // Убедиться, что результаты поиска пропали
        Assert.assertTrue("Search results did not disappear!", resultsAreGone);
    }

    @Test
    public void testResultsContainSearchWord() throws InterruptedException
    {
        String searchWord = "Java";

        // Пропустить онбординг
        waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchWord,
                "Cannot find search input!",
                5);

        // Проверка того, что в результатах поиска найдено несколько статей
        List<WebElement> searchResults = waitForSeveralElementsPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results!",
                5);

        // Проверка на случай, если в результатах поиска нету результатов
        Assert.assertFalse("No search results found!", searchResults.isEmpty());

        // Проверка того, что в полученных результатах есть искомое слово
        for (WebElement result : searchResults) {
            String title = result.getText();
            Assert.assertTrue("Search results does not contain expected word!", title.toLowerCase().contains(searchWord.toLowerCase()));
        }
    }

    // РЕАЛИЗАЦИЯ МЕТОДОВ

    private boolean waitForElementNotPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));
    }

    private List<WebElement> waitForSeveralElementsPresent(By by, String error_message, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private WebElement waitForElementAndClick(By by, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String searchWord, String error_message, int timeOutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.sendKeys(searchWord);
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
