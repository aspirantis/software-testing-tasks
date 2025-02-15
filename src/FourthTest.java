import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.*;

public class FourthTest extends CoreTestCase
{
    private lib.ui.MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();

        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testAssertElementHasText() throws InterruptedException {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        MainPageObject.assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }

    @Test
    public void testSearchPresence() throws InterruptedException {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find SKIP button!",
                5);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input!",
                5);

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Java",
                "Can not find 'Java'!",
                5);

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/page_list_item_title' and @text='Java (programming language)']"),
                "Cannot find 'Java (programming language)'!",
                5);

        MainPageObject.assertElementHasText(
                By.xpath("//android.widget.TextView[@text= 'write once, run anywhere']"),
                "write once, run anywhere",
                "Cannot find info about Java!",
                5
        );

    }
}




