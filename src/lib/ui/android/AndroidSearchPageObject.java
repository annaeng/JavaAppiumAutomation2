package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.SearchPageObject;

public class AndroidSearchPageObject extends SearchPageObject {

  static {
    SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Wikipedia durchsuchen')]";
            SEARCH_INPUT = "xpath://*[contains(@text,'Suchen…')]";
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']";
            SEARCH_RESULTS_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[contains(@text,'{SUBSTRING}')]";
            SEARCH_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']"; //By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
            SEARCH_FIELD_ID = "id:org.wikipedia:id/search_src_text";
            SEARCH_IS_CLEARED_ELEMENT = "id:org.wikipedia:id/search_empty_message";
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
            CHANGE_LANGUAGE_ELEMENT = "id:org.wikipedia:id/search_lang_button";
            CHANGE_LANGUAGE_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/localized_language_name'][@text='{SUBSTRING}']";
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
            SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text='Keine Ergebnisse gefunden']"
            ;
  }
  public  AndroidSearchPageObject(AppiumDriver driver)
  {
    super(driver);
  }


}
