package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class iOSSearchPageObject extends SearchPageObject {

  static {
    SEARCH_INIT_ELEMENT = "xpath://XCUIElementTypeSearchField[@name='Search Wikipedia']";
    SEARCH_INPUT = "xpath://XCUIElementTypeSearchField";

    //SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
    SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";

    //SEARCH_RESULTS_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]";
    SEARCH_RESULTS_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{SUBSTRING}')]";

    //SEARCH_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']"; //By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
    SEARCH_BY_SUBSTRING_TPL = "xpath://XCUIElementTypeLink[@name,'{SUBSTRING}']";

    //SEARCH_FIELD_ID = "id:org.wikipedia:id/search_src_text";
    //SEARCH_IS_CLEARED_ELEMENT = "id:org.wikipedia:id/search_empty_message";
    SEARCH_CANCEL_BUTTON = "id:Close";

    //CHANGE_LANGUAGE_ELEMENT = "id:org.wikipedia:id/search_lang_button";
    //CHANGE_LANGUAGE_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/localized_language_name'][@text='{SUBSTRING}']";

    SEARCH_RESULT_ELEMENT = "xpath://XCUIElementTypeApplication[@name='Wikipedia']//XCUIElementTypeLink";
    SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
    ;
  }

  public  iOSSearchPageObject(AppiumDriver driver)
  {
    super(driver);
  }
}
