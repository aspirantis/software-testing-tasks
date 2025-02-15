package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject
{
    private static final String
        READING_LIST_TPL = "//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{READING_LIST_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//android.view.ViewGroup[@resource-id='org.wikipedia:id/page_list_item_container' and .//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and contains(@text, '{TITLE}')]]";

    private static String getReadingListXpathByName(String name_of_reading_list)
    {
        return READING_LIST_TPL.replace("{READING_LIST_NAME}", name_of_reading_list);
    }

    private static String getSavedReadingListXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AndroidDriver driver)
    {
        super(driver);
    }

    public void openReadingListByName(String name_of_reading_list)
    {
        String readingListNameXpath = getReadingListXpathByName(name_of_reading_list);
        this.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title' and @text='" + name_of_reading_list + "']"),
                "Cannot find created reading list!",
                5);
    }

    public void swipeByArticleToDelete(String article_title) throws InterruptedException
    {
        String articleXpath = getReadingListXpathByName(article_title);

        this.waitForArticleToAppearByTitle(article_title);

        this.swipeElementToLeft(
                By.xpath(articleXpath),
                "Cannot find saved article!",
                5);

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String articleXpath = getReadingListXpathByName(article_title);
        this.waitForElementPresent(By.xpath(articleXpath),"Cannot find saved article by title " + article_title, 15);
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String articleXpath = getReadingListXpathByName(article_title);
        this.waitForElementNotPresent(By.xpath(articleXpath),"Saved article still present with title " + article_title, 15);
    }

}
