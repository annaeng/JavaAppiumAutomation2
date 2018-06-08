package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SearchPageObject extends MainPageObject {
    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Wikipedia durchsuchen')]",
            SEARCH_INPUT = "//*[contains(@text,'Suchen…')]",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULTS_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]",
            SEARCH_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']", //By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            SEARCH_FIELD_ID = "org.wikipedia:id/search_src_text",
            SEARCH_IS_CLEARED_ELEMENT = "org.wikipedia:id/search_empty_message",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            CHANGE_LANGUAGE_ELEMENT = "org.wikipedia:id/search_lang_button",
            CHANGE_LANGUAGE_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/localized_language_name'][@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='Keine Ergebnisse gefunden']"
    ;


    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultsSearchElement(String substring) {
        return SEARCH_RESULTS_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultsSearchElements(String substring) {
        return SEARCH_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getLanguage(String substring){
        return CHANGE_LANGUAGE_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES METHODS */


    public void initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find search input after clicking search init element");
    }

    public void initChangeLanguage(){
        this. waitForElementAndClick( By.id(CHANGE_LANGUAGE_ELEMENT), "Cannot find 'Language' to change language", 5);
    }

    public void changeLanguage (String substring){
        String language_to_change = getLanguage(substring);
        this.waitForElementAndClick(By.xpath(language_to_change), "Cannot find " + substring + " to change language", 5);
        //By.id("org.wikipedia:id/localized_language_name"),
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input ", 10);
    }

    public WebElement waitForSubtitleElement(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        return this.waitForElementPresent(By.xpath(search_result_xpath),"Cannot find search result with subtitle " + substring, 15 );
    }

    public String getArticleSubtitle(String substring){
        WebElement subtitle_element = waitForSubtitleElement(substring);
        return subtitle_element.getAttribute("text");
    }

    public void clickOnSearchResult(String substring) {
        String search_result = getResultsSearchElements(substring);
        this.waitForElementAndClick(By.xpath(search_result), "Cannot find and click search result with substring " + substring, 5);
    }

    public void checkSearchResults(String substring) {
        String search_results_xpath = getResultsSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_results_xpath), "Not every search result contains " + substring + " by searching " + substring, 15);
    }

    public void waitForCancelButtonAppear (){
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "",10);
    }

    public void clearSearchField() {
        this.waitForElementAndClear(By.id(SEARCH_FIELD_ID), "Cannot find search field", 10);
    }
//
//    public void waitForSearchIsCleared() {
//        this.waitForElementPresent(By.id(SEARCH_IS_CLEARED_ELEMENT), "Search is not cleared", 15);
//    }

    public WebElement waitForSearchIsCleared (String substring){
        return this.waitForElementPresent(By.id(SEARCH_IS_CLEARED_ELEMENT), "Search is not cleared", 15);
    }
    public String getAttribute(String substring){
        WebElement attribute_element = waitForSearchIsCleared (substring);
        return attribute_element.getAttribute("text");
    }

    public void waitForCancelAppear() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button 'X' BY to cancel search", 10);
    }

    public void waitForCancelDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "SEarch cancel button 'X' is still present on the page", 5);
    }

    public int getAmountOfFoundArticles ()
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT_ELEMENT), "Cannot find anything by the request ", 15);
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));
    }

    public void waitForEmptyResultsLabel(){
        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Cannot find empty result label ", 15);
    }
    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
    }

}
