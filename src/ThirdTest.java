import lib.CoreTestCase;
import lib.ui.MainPageObject;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class ThirdTest extends CoreTestCase
{
    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }


    @Test
    public void testassertElementHasText() throws InterruptedException {
        String searchWord = "Java";

        // Пропустить онбординг
    MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
    MainPageObject. waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
    MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        // Кликаем по найденному слову
    MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        // Проверяем, присутствует ли ожидаемый текст
    MainPageObject.assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }

    @Test
    public void testTextPresenceAndCancelSearch() throws InterruptedException {
        String searchWord = "Python";

        // Пропустить онбординг
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Python",
                "Can not find 'Python'!",
                5);

        // Проверка того, что в результатах поиска найдено несколько статей. Специально ищем по специальному, особому слову в заглавии статьи
        List<WebElement> searchResults = MainPageObject.waitForSeveralElementsPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, 'Pythonidae')]"),
                "Cannot find several pro-Python mentions in search results!",
                5);

        // Проверка того, что количество результатов больше двух
        assertTrue(
                "Less than 2 search results found!",
                searchResults.size() > 1);

        // Сброс результатов поиска
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find cancel search button!",
                5);

        // Проверка того, что результаты поиска исчезли
        boolean resultsAreGone = MainPageObject.waitForElementNotPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search results are still visible after canceling search!",
                5
        );

        // Убедиться, что результаты поиска пропали
        assertTrue("Search results did not disappear!", resultsAreGone);
    }

    @Test
    public void testResultsContainSearchWord() throws InterruptedException {
        String searchWord = "Java";

        // Пропустить онбординг
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        // Кликнуть по строке поиска
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        // Ввести искомое слово
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                searchWord,
                "Cannot find search input!",
                5);

        // Проверка того, что в результатах поиска найдено несколько статей
        List<WebElement> searchResults = MainPageObject.waitForSeveralElementsPresent(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Cannot find search results!",
                5);

        // Проверка на случай, если в результатах поиска нету результатов
        assertFalse("No search results found!", searchResults.isEmpty());

        // Проверка того, что в полученных результатах есть искомое слово
        for (WebElement result : searchResults) {
            String title = result.getText();
            assertTrue("Search results does not contain expected word!", title.toLowerCase().contains(searchWord.toLowerCase()));
        }
    }

}
