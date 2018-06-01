import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0.1");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\e510739\\Desktop\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Wikipedia durchsuchen')]"),
                "CANNOT FIND 'Wikipedia durchsuchen' INPUT",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Java",
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        WebElement article_from_the_list = waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Objektorientierte Programmiersprache']"),
                "CANNOT FIND 'Objektorientierte Programmiersprache'topic searching by 'Java'",
                15
        );

        String article_message = article_from_the_list.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected element!",
                "Objektorientierte Programmiersprache",
                article_message
        );
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Java",
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Objektorientierte Programmiersprache']"),
                "CANNOT FIND 'Objektorientierte Programmiersprache'topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Wikimedia-Begriffsklärungsseite']"),
                "CANNOT FIND 'Wikimedia-Begriffsklärungsseite' topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Skriptsprache']"),
                "CANNOT FIND 'Skriptsprache' topic searching by 'Java'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'Java')]"),
                "Not every search result contains 'Java' by searching 'Java'",
                15
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                10
        );

        WebElement empty_element = waitForElementPresent(
                By.id("org.wikipedia:id/search_empty_message"),
                "Search is not cleared",
                15
        );

        String empty_message = empty_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected element!",
                "Die freie Enzyklopädie in deiner Sprache suchen und lesen",
                empty_message
        );

//        waitForElementAndClick(
//                By.id("org.wikipedia:id/search_close_btn"),
//                "Cannot find 'X' BY ID to cancel search ",
//                5
//        );
//
//        waitForElementNotPresent (
//                By.id("org.wikipedia:id/search_close_btn"),
//                "'X' still present on the page",
//                5
//        );
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Java",
                "CANNOT FIND search input 'Suchen…'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Objektorientierte Programmiersprache']"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title!",
                "Java (Programmiersprache)",
                article_title
        );

    }

    @Test
    public void testSwipeArticle() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        //my App is in German, first I have to change language to English, so this article appears
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_lang_button"),
                "Cannot find 'Language' to change language",
                5
        );

        waitForElementAndClick(
                //By.id("org.wikipedia:id/localized_language_name"),
                By.xpath("//*[@resource-id='org.wikipedia:id/localized_language_name'][@text='English']"),
                "Cannot find 'English' to change language",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Appium",
                "CANNOT FIND search input 'Suchen…'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        swipeUpToFindElement(
                By.xpath("//*[@text='Seite im Browser ansehen']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void testSaveFirstArticleToMyList() {
        //Looking for a search field on the start page of Wiki and click on it
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        //Typing a name (what we are looking for) into the search field  on the new screen
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Java",
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        //Looking for an article with the certain description of the search result and click on it
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Objektorientierte Programmiersprache']"),
                "Cannot find 'Wikipedia durchsuchen' input",
                5
        );

        //Checking the article is opened and it is the right article
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        //First step to save the article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Weitere Optionen']"),
                "Cannot find button to open article options",
                10
        );

        //Click add the opened article to the reading list
        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[@index='3']"),
                //*[@text='Zur Leseliste hinzufügen']"),
                "Cannot find option to add article to reading list ",
                5
        );

        //Got It Button
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Verstanden' tip overlay",
                5
        );

        //first we need to clear the field with the name of articles folder
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set the name of articles folder",
                5
        );

        //now we type the name, how we want to name our folder in the reading list
        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        //OK - saving our reading list
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        //closing the article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close article, cannot find X link",
                5
        );

        //Go to My Lists on the Start Page
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Meine Listen']"),
                "Cannot find navigation button to My lists",
                5
        );

        //Looking for our reading list and go into it
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        //to delete we need to swipe
        swipeElementToLeft(
                //By.xpath("//*[@text='Java (Programmiersprache)']"),
                By.xpath("//android.widget.TextView[@text='Java (Programmiersprache)']"),
                "Cannot find saved article"
        );

        //make sure the article is deleted
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (Programmiersprache)']"),
                "Cannot delete saved article",
                5
        );

    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() {
//      My Wikipedia App is German, to find an article about Appium I have to change the language first
        findAndSaveFirstArticleToANewFolder(); //saving the first article to the new folder and go back to the homepage
        findArticleInEnglish();  //finding a new (second) article to save
        saveArticleToExistingFolder(); //saving a second article to the existing folder and go to the homepage
        deleteFirstArticleFromList(); //go to the folder with saved articles and delete a first one, go to homepage
        makeSureSecondArticleStillInTheFolderAndTheTitleIsCorrect(); //go to the folder with saved article and make sure it is the second one
    }


    @Test
    public void testAmountOfNotEmptySearch() {
        searchArticleAndMakeSureYouGotResults();
    }

    @Test
    public void testAmountOfEmptySearch() {
        AmountOfEmptySearch();
    }


    @Test
    public void testAssertTitlePresent() {
        AssertTitlePresent();
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.clear();
        return element;
    }

    protected void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);


        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {

            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                15);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(350)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private void findAndSaveFirstArticleToANewFolder() {
        //1. Save the first article

        //Looking for a search field on the start page of Wiki and click on it
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        //Typing a name (what we are looking for) into the search field  on the new screen
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Java",
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        //Looking for an article with the certain description of the search result and click on it
        String description_of_the_first_article = "Objektorientierte Programmiersprache";

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + description_of_the_first_article + "']"),
                "Cannot find 'Wikipedia durchsuchen' input",
                5
        );

        //Checking the article is opened and it is the right article
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        //First step to save the article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Weitere Optionen']"),
                "Cannot find button to open article options",
                10
        );

        //Click add the opened article to the reading list
        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[@index='3']"),
                //*[@text='Zur Leseliste hinzufügen']"),
                "Cannot find option to add article to reading list ",
                5
        );

        //Got It Button
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Verstanden' tip overlay",
                5
        );

        //first we need to clear the field with the name of articles folder
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set the name of articles folder",
                5
        );

        //now we type the name, how we want to name our folder in the reading list
        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        //OK - saving our reading list
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        //closing the article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close article, cannot find X link",
                5
        );

        //Go to My Lists on the Start Page
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Meine Listen']"),
                "Cannot find navigation button to My lists",
                5
        );

        //Looking for our reading list and click Learning programming
        waitForElementAndClick(
                //By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                //By.xpath("//android.widget.TextView[@text='Learning programming']"),
                By.xpath("//android.widget.TextView"),
                "Cannot find created folder",
                10
        );

        //go to the first article
        //String title_of_the_first_article = "Java (Programmiersprache)";
        waitForElementAndClick(
                By.xpath("//*[@text='Java (Programmiersprache)']"),
                //By.xpath("//*[@text='" + title_of_the_first_article + "']"),
                "Cannot get into the saved first article",
                10
        );

//        waitForElementPresent(
//                By.xpath("//*[@text='Java (Programmiersprache)']"),
//                //By.xpath("//*[@text='" + title_of_the_first_article + "']"),
//                "Cannot find saved first article",
//                15
//        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close article, cannot find X link",
                5
        );

        //close the list of articles
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot go to the homepage",
                5
        );

        //going to the homepage
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Entdecken']"),
                "Cannot find navigation button to the Homepage",
                5
        );
    }

    private void findArticleInEnglish() {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Wikipedia durchsuchen' input BY ID",
                5
        );

        //my App is in German, first I have to change language to English, so this article comes on the top
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_lang_button"),
                "Cannot find 'Language' to change language",
                5
        );

        waitForElementAndClick(
                //By.id("org.wikipedia:id/localized_language_name"),
                By.xpath("//*[@resource-id='org.wikipedia:id/localized_language_name'][@text='English']"),
                "Cannot find 'English' to change language",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                "Appium",
                "CANNOT FIND search input 'Suchen…'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find 'Appium' in search",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
    }

    private void saveArticleToExistingFolder() {

        //First step to save the article
        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='Weitere Optionen']"),
                "Cannot find button to open article options",
                10
        );

        //Click add the opened article to the reading list
        waitForElementAndClick(
                By.xpath("//android.widget.LinearLayout[@index='3']"),
                //*[@text='Zur Leseliste hinzufügen']"),
                "Cannot find option to add article to reading list ",
                5
        );

        //choose adding to the existing list
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='Learning programming']"),
                //*[@text='Zur Leseliste hinzufügen']"),
                "Cannot find option to add article to existing reading list ",
                15
        );

        //close an article with X on the left
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close article, cannot find X link",
                5
        );
    }

    private void deleteFirstArticleFromList() {
        //Go to My Lists on the Start Page
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Meine Listen']"),
                "Cannot find navigation button to My lists",
                5
        );

        //Looking for our reading list and go into it
        String name_of_folder = "Learning programming";
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        //to delete we need to swipe
        swipeElementToLeft(
                //By.xpath("//*[@text='Java (Programmiersprache)']"),
                By.xpath("//android.widget.TextView[@text='Java (Programmiersprache)']"),
                "Cannot find saved article"
        );

        //make sure the first article is deleted
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (Programmiersprache)']"),
                "Cannot delete saved article",
                5
        );

        //close the list
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close article, cannot find X link",
                5
        );

        //going to the homepage
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Entdecken']"),
                "Cannot find navigation button to the Homepage",
                5
        );
    }

    private void makeSureSecondArticleStillInTheFolderAndTheTitleIsCorrect() {

        //Go to My Lists on the Start Page
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Meine Listen']"),
                "Cannot find navigation button to My lists",
                5
        );

        //Looking for our reading list and click Learning programming
        waitForElementAndClick(
                //By.xpath("//android.widget.TextView[@text='" + name_of_folder + "']"),
                //By.xpath("//android.widget.TextView[@text='Learning programming']"),
                By.xpath("//android.widget.TextView"),
                "Cannot find created folder",
                10
        );

        //go to the article still in the list
        //String title_of_the_article = "Appium";
        waitForElementAndClick(
                By.xpath("//*[@text='Appium']"),
                //By.xpath("//*[@text='" + title_of_the_article + "']"),
                "Cannot get into the saved first article",
                10
        );


        // check the title!!!
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "We see unexpected title!",
                "Appium",
                article_title
        );

        //close the article -> after that you are in the folder which you need to close too
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot close  Appium article, cannot find X link",
                5
        );

        //close the folder with articles
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Nach oben']"),
                "Cannot go to the homepage",
                5
        );

        //going to the homepage
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Entdecken']"),
                "Cannot find navigation button to the Homepage",
                5
        );
    }

    private void searchArticleAndMakeSureYouGotResults() {

        //Looking for a search field on the start page of Wiki and click on it
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Wikipedia durchsuchen')]"),
                "CANNOT FIND 'Wikipedia durchsuchen' INPUT",
                5
        );

        //Typing a name (what we are looking for) into the search field  on the new screen

        String search_line = "Linkin Park/Diskografie";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                search_line,
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request " + search_line,
                15
        );

        int amount_of_search_results = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void AmountOfEmptySearch() {
        //Looking for a search field on the start page of Wiki and click on it
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Wikipedia durchsuchen')]"),
                "CANNOT FIND 'Wikipedia durchsuchen' INPUT",
                5
        );

        //Typing a name (what we are looking for) into the search field  on the new screen
        String search_line = "klrjgoifsgju";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                search_line,
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='Keine Ergebnisse gefunden']";
        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );

        assertElementNotPresent(
                By.xpath(search_result_locator),
                "We have found some results by request " + search_line
        );

    }

    private void assertElementNotPresent(By by, String error_message)
    {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0)
        {
            String default_message = "An element '" + by.toString() + "' suppoused to be not present";
            throw new AssertionError(default_message + "" + error_message);
        }
    }

    private void AssertTitlePresent() {
        //Looking for a search field on the start page of Wiki and click on it
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Wikipedia durchsuchen')]"),
                "CANNOT FIND 'Wikipedia durchsuchen' INPUT",
                5
        );

        //Typing a name (what we are looking for) into the search field  on the new screen
        String search_line = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Suchen…')]"),
                search_line,
                "CANNOT FIND search input 'Suchen…'",
                5
        );

        //Open the article like this: looking for an article with the certain description of the search result and click on it
        String title_of_the_article = "Java (Programmiersprache)";
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + title_of_the_article + "']";

        waitForElementAndClick(
                By.xpath(search_result_locator),
                "Cannot find an article with the description" + title_of_the_article,
                5
        );

        //assert title
        String title_locator = "//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='" + title_of_the_article + "']";
        int amount_of_elements_title = getAmountOfElements(
                By.xpath(title_locator)
        );

        Assert.assertTrue(
                "We didn't find element 'title' at this article",
                amount_of_elements_title < 1
        );

//        // assert title with waitForElementPresent
//        String title_in_the_article_locator = "//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (Programmiersprache)']";
//        waitForElementPresent(
//                By.xpath(title_in_the_article_locator),
//                "Cannot find title " + title_of_the_article,
//                15
//        );
//
//        assertElementPresent(
//                By.xpath(title_in_the_article_locator),
//                "There is no element 'title' found for the article  " + title_of_the_article
//        );

    }

    private void assertElementPresent(By by, String error_message)
    {
        int amount_of_title_element = getAmountOfElements(by);
        if (amount_of_title_element < 0)
        {
            String default_message = " Element 'title' " + by.toString() + "' suppoused to be present";
            throw new AssertionError(default_message + "" + error_message);
        }
    }
}



