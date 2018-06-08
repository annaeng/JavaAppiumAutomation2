package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ArticlePageObject extends MainPageObject {

    private static final String
            TITLE_ID = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='Seite im Browser ansehen']",
            OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='Weitere Optionen']",
            OPTIONS_ADD_TO_MY_LIST_BUTTON = "//android.widget.LinearLayout[@index='3']",       //*[@text='Zur Leseliste hinzufügen']"),
            ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
            INPUT_MY_FIELD_LIST_NAME = "org.wikipedia:id/text_input",
            MY_LIST_OK_BUTTON = "//*[@text='OK']",
            CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Nach oben']",
            EXISTING_READING_LIST_TPL = "//android.widget.TextView[@text='{LIST_NAME}']",   //*[@text='Zur Leseliste hinzufügen']"),   //'Learning programming'
            TITLE_LOCATOR = "//*[@resource-id='org.wikipedia:id/view_page_title_text']";
            //TITLE_LOCATOR = "//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Title']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getReadingList(String name_of_folder) {
        return EXISTING_READING_LIST_TPL.replace("{LIST_NAME}", name_of_folder);
    }
    /* TEMPLATES METHODS */


    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.id(TITLE_ID), "Cannot find article title the page",15);
    }

    public String getArticleTitle(){
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }
    public void swipeToFooter(){
        this. swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Cannot find the end of the article", 20);
    }


    public void addArticleToMyList (String name_of_folder){
        //First step to save the article
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON), "Cannot find button to open article options", 10);

        //Click add the opened article to the reading list
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON), "Cannot find option to add article to reading list ", 5);

        //Got It Button
        this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY), "Cannot find 'Verstanden' tip overlay", 5);

        //first we need to clear the field with the name of articles folder
        this.waitForElementAndClear(By.id(INPUT_MY_FIELD_LIST_NAME), "Cannot find input to set the name of articles folder", 5);

        //now we type the name, how we want to name our folder in the reading list
        this.waitForElementAndSendKeys(By.id(INPUT_MY_FIELD_LIST_NAME), name_of_folder, "Cannot put text into articles folder input", 5);

        //OK - saving our reading list
        this.waitForElementAndClick(By.xpath(MY_LIST_OK_BUTTON), "Cannot press OK button", 5);
    }

    public void addArticleToExistingList (String name_of_folder){
        //First step to save the article
        this.waitForElementAndClick(By.xpath(OPTIONS_BUTTON), "Cannot find button to open article options", 10);

        //Click add the opened article to the reading list
        this.waitForElementAndClick(By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON), "Cannot find option to add article to reading list ", 5);

//        //Got It Button
//        this.waitForElementAndClick(By.id(ADD_TO_MY_LIST_OVERLAY), "Cannot find 'Verstanden' tip overlay", 5);

        String reading_list = getReadingList(name_of_folder);
        this.waitForElementAndClick(By.xpath(reading_list), "Cannot find option to add article to existing reading list " + name_of_folder, 15);
    }

    public void closeArcticle (){
        this.waitForElementAndClick(By.xpath(CLOSE_ARTICLE_BUTTON), "Cannot close article, cannot find X link", 5);
    }

    public int getAmountOfElementsTitle (){
        this.waitForElementPresent(By.xpath(TITLE_LOCATOR),"Cannot find any title",15);
        return this.getAmountOfElements(By.xpath(TITLE_LOCATOR));
    }


}
