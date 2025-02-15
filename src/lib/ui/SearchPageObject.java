package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject
{
    private static final String

        SEARCH_SKIP_ELEMENT = "org.wikipedia:id/fragment_onboarding_skip_button",
        SEARCH_INPUT = "org.wikipedia:id/search_container",
        SEARCH_TEXT = "org.wikipedia:id/search_src_text",
        SEARCH_CLOSE_BUTTON = "org.wikipedia:id/search_close_btn",
//        SEARCH_RESULT_BY_SUBSTRING_TPL = "//android.widget.TextView[contains(@text, '{SUBSTRING}')]",
        SEARCH_RESULT_BY_SUBSTRING_TPL ="//*[@resource-id='org.wikipedia:id/search_results_list']" +
            "[.//*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']]" +
            "[.//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']]",
        SEARCH_RESULT_ELEMENT = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title']",
        SEARCH_EMPTY_RESULTS_ELEMENT = "//*[contains(@resource-id, 'org.wikipedia:id/results_text') and contains(@text, 'No results')]";


    public SearchPageObject(AndroidDriver driver)
    {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String title)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{TITLE}", title);
    }

    private static String getResultSearchElementByTiTleAndDescription(String title, String description)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */


    public void initSearchInput()
    {
        this.waitForElementAndClick(By.id(SEARCH_SKIP_ELEMENT), "Cannot find and click SKIP button!", 5);
        this.waitForElementAndClick(By.id(SEARCH_INPUT), "Cannot find and click search init element!", 5);
        this.waitForElementPresent(By.id(SEARCH_INPUT), "Cannot find search init element after clicking!", 5);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CLOSE_BUTTON), "Cannot find search close button!", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CLOSE_BUTTON), "Search close button is still present!", 5);
    }

    public void clickCloseSearch()
    {
        this.waitForElementAndClick(By.id(SEARCH_CLOSE_BUTTON), "Cannot find and click search close button!", 5);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementAndSendKeys(By.id(SEARCH_TEXT ), search_line,"Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String title)
    {
        String search_result_xpath = getResultSearchElement(title);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + title, 5);
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElementByTiTleAndDescription(title, description);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with title '"
                + title + "' and description '" + description + "'", 5);
    }

    public void clickByArticleWithSubString(String title)
    {
        String search_result_xpath = getResultSearchElement(title);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring ", 10);
    }

    public int getAmountOfFoundArticles() throws InterruptedException {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Can not find anything by the request", 5);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULTS_ELEMENT), "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultOfSearch() throws InterruptedException {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }
}
