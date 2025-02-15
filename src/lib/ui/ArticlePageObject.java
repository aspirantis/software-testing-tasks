package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject
{
    public static final String
        TITLE1 = "//android.widget.TextView[@text='Java (programming language)']",
        TITLE2 = "//android.widget.TextView[@text='Appium']",
        TITLE3 = "(//android.widget.TextView[@text='Appium'])[1]",
        FOOTER_ELEMENT = "//android.view.View[@content-desc='View article in browser']",
        SAVE_BUTTON ="org.wikipedia:id/page_save",
        ADD_TO_LIST_BUTTON ="org.wikipedia:id/snackbar_action",
        INPUT_READING_LIST_NAME = "org.wikipedia:id/text_input",
        INPUT_READING_LIST_DESCRIPTION = "org.wikipedia:id/secondary_text_input",
        OK_BUTTON = "android:id/button1",
        VIEW_LIST = "org.wikipedia:id/snackbar_action",
        SEARCH_FROM_ARTICLE = "org.wikipedia:id/page_toolbar_button_search";



    public ArticlePageObject(AndroidDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement1()
    {
        return this.waitForElementPresent(By.xpath(TITLE1),"Cannot find article title on page!",10);
    }

    public WebElement waitForTitleElement2()
    {
        return this.waitForElementPresent(By.xpath(TITLE2),"Cannot find article title on page!",10);
    }

    public String getArticleTitle1()
    {
        WebElement titleElement = waitForTitleElement1();
        return titleElement.getText();
    }

    public String getArticleTitle2()
    {
        WebElement titleElement = waitForTitleElement2();
        return titleElement.getText();
    }

    public void swipeToFooter() throws InterruptedException
    {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of srticle!", 20);
    }

    public void addArticleToMyListFirstTime(String name_of_folder)
    {
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find Save button!",
                5);

        this.waitForElementAndClick(
                By.id(ADD_TO_LIST_BUTTON),
                "Cannot find Add to reading list!",
                5);

        this.waitForElementAndSendKeys(
                By.id(INPUT_READING_LIST_NAME),
                "My Reading List",
                "Cannot enter name of the reading list!",
                5);

        this.waitForElementAndSendKeys(
                By.id(INPUT_READING_LIST_DESCRIPTION),
                "QA articles",
                "Cannot enter description of the reading list!",
                5);

        this.waitForElementAndClick(
                By.id(OK_BUTTON),
                "Can not press OK button!",
                5);

        this.waitForElementAndClick(
                By.id(VIEW_LIST),
                "Cannot find saved reading list!",
                5);
    }

    public void addArticleToMyList(String name_of_reading_list)
    {
        this.waitForElementAndClick(
                By.id(SAVE_BUTTON),
                "Cannot find Save button!",
                5);

        this.waitForElementAndClick(
                By.id(ADD_TO_LIST_BUTTON),
                "Cannot find Add to reading list!",
                5);

        this.openReadingListByName(name_of_reading_list);

        this.waitForElementAndClick(
                By.id(VIEW_LIST),
                "Cannot find saved reading list!",
                5);
    }

    public void searchFromArticle()
    {
        this.waitForElementAndClick(
                By.id(SEARCH_FROM_ARTICLE),
                "Cannot find search field!",
                5);
    }

    public void assertTitlePresent() throws InterruptedException
    {
        this.assertElementPresent(By.xpath(TITLE3), "There are several elements on page");
    }
}
