import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

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

        String  article_message = article_from_the_list.getAttribute("text");
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

        String  empty_message = empty_element.getAttribute("text");
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
    public void testCompareArticleTitle()
    {
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

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message)
    {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds)
    {
        WebElement element = waitForElementPresent(by, error_message, 10);
        element.clear();
        return  element;
    }
}
