package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject {

    private static final String
            FOLDER_BY_NAME_TPL = "xpath://android.widget.TextView[@text='{FOLDER_NAME}']",//By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']" //By.xpath("//android.widget.TextView[@text='Java (Programmiersprache)']"), //By.xpath("//*[@text='Appium']"), //By.xpath("//*[@text='" + title_of_the_article + "']"),
    ;

    /* TEMPLATES METHODS */
    private static String getFolderXpathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_folder);
    }
    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}",article_title);
    }
    /* TEMPLATES METHODS */

    public MyListsPageObject(AppiumDriver driver){
        super(driver);
    }


    public void openFolderByName (String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(folder_name_xpath, "Cannot find folder by name " + name_of_folder, 10);
        this.waitForElementAndClick(folder_name_xpath, "Cannot find folder by name " + name_of_folder, 10);
    }

    public void waitForArticleToApearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(article_xpath,"Cannot find saved article by title " + article_title,15);
    }


    public void waitForArticleToDissapearByTitle(String article_title)
    {
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(article_xpath,"Saved article still present with title " + article_title,15);
    }

    public void swipeByArticleToDelete(String article_title){
        this.waitForArticleToApearByTitle(article_title);
        String article_xpath = getFolderXpathByName(article_title);
        this.swipeElementToLeft(article_xpath, "Cannot find saved article");
        this.waitForArticleToDissapearByTitle(article_title);
    }

    public void clickOnArticleByTitle (String article_title){

        //String title_of_the_article = "Appium";
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(article_xpath, "Cannot get into the article", 10);
    }

}
