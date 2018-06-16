package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase
{

    @Test
    public void testCompareArticleTitle() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickOnSearchResult("Objektorientierte Programmiersprache");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);//looking for a title
        String article_title = ArticlePageObject.getArticleTitle();//get text from the article title

        assertEquals("We see unexpected title!", "Java (Programmiersprache)", article_title);
    }

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        //my App is in German, first I have to change language to English, so this article appears
        SearchPageObject.initChangeLanguage();
        SearchPageObject.changeLanguage("English");
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickOnSearchResult("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals("We see unexpected title!", "Appium", article_title);

        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() {

        // saving the first article to the new folder and close the article - we are on the homepage
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickOnSearchResult("Objektorientierte Programmiersprache");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected title!", "Java (Programmiersprache)", article_title);

        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();//we are on the Homepage

        //my App is in German, first I have to change language to English, so this article comes on the top
        SearchPageObject.initSearchInput();
        SearchPageObject.initChangeLanguage();
        SearchPageObject.changeLanguage("English");
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickOnSearchResult("Appium");

        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.addArticleToExistingList(name_of_folder);
        ArticlePageObject.closeArticle();// we are on the Homepage

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeByArticleToDelete(article_title);


        MyListsPageObject.waitForArticleToApearByTitle("Appium");
        MyListsPageObject.clickOnArticleByTitle("Appium");
        String left_article_title = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected title!", "Appium", left_article_title);

    }


}
