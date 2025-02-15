package lib.ui;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject
{
    private static final String
            NAVIGATE_UP = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public NavigationUI(AndroidDriver driver)
    {
        super(driver);
    }

    public void returnToSearchFromArticle()
    {
        this.waitForElementAndClick(
                By.xpath(NAVIGATE_UP),
                "Cannot find return button!",
                5);
        }


}
