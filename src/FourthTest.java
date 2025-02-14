import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class FourthTest

{

    private AndroidDriver driver;

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
        driver.rotate(ScreenOrientation.PORTRAIT);
        driver.quit();

    }


    @Test
    public void testAmountOfNonEmptySearch() throws InterruptedException {
        String searchInfo = "Linkin Park discography";

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
                searchInfo,
                "Cannot find search input!",
                5);

        String search_result_locator = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Can not find anything by the request" + searchInfo,
                10);

        int amount_of_search_elements = getAmountOfElements(By.xpath(search_result_locator));

        Assert.assertTrue(
                "We found too dew results!",
                amount_of_search_elements > 0);

    }


    @Test
    public void testAmountOfEmptySearch() throws InterruptedException {
        String searchNoResults = "dfdgherwewwr";

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
                searchNoResults,
                "Cannot find search input!",
                5);

        String empty_result_label = "//*[contains(@resource-id, 'org.wikipedia:id/results_text') and contains(@text, 'No results')]";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty results label by request:\n" + searchNoResults,
                15);

        assertElementNotPresent(
                By.xpath(searchNoResults),
                "We've found some requests by " + searchNoResults);
    }


    @Test
    public void testChangeScreenOrientationOnSearchResults() throws InterruptedException {
        String searchWord1 = "Java";

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
                searchWord1,
                "Cannot find 'Object-oriented programming language' topic searching by " + searchWord1,
                15);

        // Кликаем по найденному слову
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        String title_before_rotation = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article!",
                15);

        Thread.sleep(5000);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        Thread.sleep(5000);

        String title_after_rotation = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article!",
                15);

        Assert.assertEquals(
                "Article title have been change after screen rotation!",
                title_before_rotation,
                title_after_rotation);

        driver.rotate(ScreenOrientation.PORTRAIT);

        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "text",
                "Cannot find title of article!",
                15);

        Assert.assertEquals(
                "Article title have been change after screen rotation!",
                title_before_rotation,
                title_after_second_rotation);
    }


    @Test
    public void testSearchTestArticleInBackground() throws InterruptedException {
        String searchWord3 = "Java";

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
                searchWord3,
                "Cannot find search input!",
                5);

        waitForElementPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find article about Java'!",
                5);

        driver.runAppInBackground(Duration.ofSeconds(2));

        waitForElementPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find article about Java after returning from background!",
                5);
    }


    @Test
    public void testSaveTwoArticlesToMyList() throws InterruptedException {
        String searchWord1 = "Appium";
        String searchWord2 = "Java";

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
                searchWord1,
                "Cannot find search input!",
                5);

        // Кликаем по найденному слову
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Automation for Apps']"),
                "Cannot find Appium'!",
                5);

        // Кликнуть по кнопке Save для добавления в список для чтения
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find Save button!",
                5);

        // Подтвердить добавление в список для чтения
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find Add to reading list!",
                5);

        // Задать название списка для чтения
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "My Reading List",
                "Cannot enter name of the reading list!",
                5);

        // Задать описание списка для чтения
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/secondary_text_input"),
                "QA articles",
                "Cannot enter description of the reading list!",
                5);

        // Подтвердить изменения (выбор названия и описания списка для чтения)
        waitForElementAndClick(
                By.id("android:id/button1"),
                "Can not press OK button!",
                5);

        // Открыть список для чтения на просмотр
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find saved reading list!",
                5);

        Thread.sleep(5000);

        // Проверка наличия сохранённой статьи
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Automation for Apps']"),
                "Cannot find Appium'!",
                5);

        // Вернуться из списка для чтения на экран статьи
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find return button!",
                5);

        // Кликнуть по полю для поиска
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_toolbar_button_search"),
                "Cannot find search field!",
                5);

        // Ввести искомое слово
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchWord2,
                "Cannot find search input!",
                5);

        // Кликаем по найденному слову
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find second article! " + searchWord2,
                5);

        // Кликнуть по кнопке Save для добавления в список для чтения
        waitForElementAndClick(
                By.id("org.wikipedia:id/page_save"),
                "Cannot find Save button!",
                5);

        // Подтвердить добавление в список для чтения
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find Add to reading list!",
                5);

        // Открыть созданный список для чтения
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='My Reading List']"),
                "Cannot find Add to reading list!",
                5);

        // Подтвердить добавление в список для чтения
        waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find Add to reading list!",
                5);

        // Проверка наличия сохранённой статьи
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find Appium'!",
                5);

        // Удаление сохранённой статьи
        swipeElementToLeft(
                By.xpath("//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container' and .//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, 'Appium')]]"),
                "Cannot find saved article!",
                5);

        // Проверка наличия сохранённой статьи
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find Java'!",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find second article! " + searchWord2,
                5);

        // Проверка наличия сохранённой статьи
        waitForElementPresent(
                By.xpath("//android.widget.TextView[@text='Java (programming language)']"),
                "Cannot find title of article Java'!",
                5);

        Thread.sleep(2000);
    }


    @Test
    public void testAssertTitlePresent() throws InterruptedException {
        String searchWord1 = "Appium";

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
                searchWord1,
                "Cannot find search input!",
                5);

        // Кликаем по найденному слову
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_description' and @text='Automation for Apps']"),
                "Cannot find Appium'!",
                5);

        Thread.sleep(5000);


        assertTitlePresent(
                By.xpath("(//android.widget.TextView[@text='Appium'])[1]"),
                "Cannot find title by using assert!");
    }



    /** РЕАЛИЗАЦИЯ МЕТОДОВ **/



    private WebElement waitForElementPresent (By by, String error_message,int timeOutInSeconds) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
            wait.withMessage(error_message + "\n");
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by));
        }

    private WebElement waitForElementAndClick (By by, String error_message,int timeOutInSeconds) {
            WebElement element = waitForElementPresent(by, error_message, 10);
            element.click();
            return element;
        }

    private WebElement waitForElementAndSendKeys (By by, String searchWord, String error_message,
        int timeOutInSeconds) {
            WebElement element = waitForElementPresent(by, error_message, 10);
            element.sendKeys(searchWord);
            return element;
        }

    protected void swipeUp () throws InterruptedException {
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

    protected void swipeElementToLeft (By by, String error_message,int max_swipes) throws InterruptedException {
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

    private int getAmountOfElements (By by) throws InterruptedException {
            List elements = driver.findElements(by);
            return elements.size();

        }

    private int getAmountOfTitles (By by) {
            List element = driver.findElements(by);
            return element.size();

        }

    public void assertElementNotPresent (By by, String error_message) throws InterruptedException {
            int amount_of_elements = getAmountOfElements(by);
            if (amount_of_elements > 0) {
                String default_message = "An element '" + by.toString() + "' supposed to be present";
                throw new AssertionError(default_message + " " + error_message);
            }
        }

    public void assertTitlePresent (By by, String error_message) {
            int amount_of_titles = getAmountOfTitles(by);
            if (amount_of_titles == 0) {
                String default_message = "An element '" + by.toString() + "' supposed to be not present";
                throw new AssertionError(default_message + " " + error_message);
            }
        }

    public String waitForElementAndGetAttribute (By by, String attribute, String error_message,long timeOutInSeconds) {
            WebElement element = waitForElementPresent(by, error_message, 10);
            return element.getDomAttribute(attribute);
        }

}




