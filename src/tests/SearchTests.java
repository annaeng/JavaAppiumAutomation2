package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        //SearchPageObject.waitForSubtitleElement("Objektorientierte Programmiersprache");
        String article_subtitle = SearchPageObject.getArticleSubtitle("Objektorientierte Programmiersprache");

        assertEquals("We see unexpected element!", "Objektorientierte Programmiersprache", article_subtitle);
    }


    @Test
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSubtitleElement("Objektorientierte Programmiersprache");
        SearchPageObject.waitForSubtitleElement("Wikimedia-Begriffsklärungsseite");
        SearchPageObject.waitForSubtitleElement("Skriptsprache");
        SearchPageObject.checkSearchResults("Java");
        SearchPageObject.waitForCancelButtonAppear();// a new one. added to make sure the X appears
        SearchPageObject.clearSearchField();

        String empty_message = SearchPageObject.getAttribute("text");
        assertEquals("We see unexpected element!", "Die freie Enzyklopädie in deiner Sprache suchen und lesen", empty_message);

        SearchPageObject.waitForCancelAppear();
        SearchPageObject.waitForCancelDisappear();

    }

    @Test
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park/Diskografie";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        assertTrue("We found too few results!", amount_of_search_results > 0);
    }

    @Test
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "kdfjsrgiraeghn";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }


    @Test
    public void testAssertTitlePresent() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickOnSearchResult("Objektorientierte Programmiersprache");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String article_title = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected title!", "Java (Programmiersprache)", article_title);

        int amount_of_search_results = ArticlePageObject.getAmountOfElementsTitle();
        assertTrue("We didn't find element 'title' at this article", amount_of_search_results > 0);
        ArticlePageObject.closeArticle();

    }

}

//        //close the list of articles
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
//                "Cannot close the folder with article(s)",
//                5
//        );

//        //going to the homepage
//        MainPageObject.waitForElementAndClick(
//                By.xpath("//android.widget.FrameLayout[@content-desc='Entdecken']"),
//                "Cannot find navigation button to the Homepage",
//                5
//        );
