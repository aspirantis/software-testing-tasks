package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase
{
    @Test
    public void testSaveTwoArticlesToMyList() throws InterruptedException
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Java (programming language)");
        SearchPageObject.clickByArticleWithSubString("Java (programming language)");

        ArticlePageObject.waitForTitleElement1();
        String first_article_title = ArticlePageObject.getArticleTitle1();
        String name_of_reading_list = "My Reading List";
        ArticlePageObject.addArticleToMyListFirstTime(name_of_reading_list);
        ArticlePageObject.waitForTitleElement1();
        NavigationUI.returnToSearchFromArticle();

        //тут начался поиск второй статьи
        ArticlePageObject.searchFromArticle();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.waitForSearchResult("Appium");
        SearchPageObject.clickByArticleWithSubString("Appium");
        ArticlePageObject.waitForTitleElement2();
        String second_article_title = ArticlePageObject.getArticleTitle2();
        ArticlePageObject.addArticleToMyList(name_of_reading_list);
        ArticlePageObject.waitForTitleElement2();
        MyListsPageObject.swipeByArticleToDelete(second_article_title);

    }
}
