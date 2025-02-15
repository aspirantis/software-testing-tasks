package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;


public class ArticleTests extends CoreTestCase
{

    @Test
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Java (programming language)");
        SearchPageObject.clickByArticleWithSubString("Java (programming language)");
        String article_title = ArticlePageObject.getArticleTitle1();

        assertEquals(
                "We see unexpected title!",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void testSwipeArticle() throws InterruptedException
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Java (programming language)");
        SearchPageObject.clickByArticleWithSubString("Java (programming language)");

        ArticlePageObject.waitForTitleElement1();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testAssertTitlePresent() throws InterruptedException
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.waitForSearchResult("Appium");
        SearchPageObject.clickByArticleWithSubString("Appium");
        String article_title = ArticlePageObject.getArticleTitle2();
        ArticlePageObject.waitForTitleElement2();
        SearchPageObject.clickByArticleWithSubString("Appium");
        ArticlePageObject.assertTitlePresent();
    }
}
