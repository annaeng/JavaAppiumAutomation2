package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

  private static final String PLATFORM_IOS = "ios";
  private static final String PLATFORM_ANDROID = "android";
  private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";


  private static Platform instance;

  private Platform() { }

  public static Platform getInstance() {
    if (instance == null) {
      instance = new Platform();
    }
    return instance;
  }


  public AppiumDriver getDriver() throws Exception {
    URL URL = new URL(APPIUM_URL);
    if (this.isAndroid()) {
      return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());
    } else if (this.isIOS()) {
      return new IOSDriver(URL, this.getIOSDesiredCapabilities());
    } else {
      throw new Exception("Cannot detect type of the Driver. Platform value: " + this.getPlatformVar());
    }
  }


  public boolean isAndroid() {
    return isPlatform(PLATFORM_ANDROID);
  }


  public boolean isIOS() {
    return isPlatform(PLATFORM_IOS);
  }


  private DesiredCapabilities getAndroidDesiredCapabilities() {
    DesiredCapabilities DesiredCapabilities = new DesiredCapabilities();
    DesiredCapabilities.setCapability("platformName", "Android");
    DesiredCapabilities.setCapability("deviceName", "AndroidTestDevice");
    DesiredCapabilities.setCapability("platformVersion", "8.1");
    DesiredCapabilities.setCapability("automationName", "Appium");
    DesiredCapabilities.setCapability("appPackage", "org.wikipedia");
    DesiredCapabilities.setCapability("appActivity", ".main.MainActivity");
    return DesiredCapabilities;
  }


  private DesiredCapabilities getIOSDesiredCapabilities() {
    DesiredCapabilities DesiredCapabilities = new DesiredCapabilities();
    DesiredCapabilities.setCapability("app", "//Users/annaryapolova/Documents archive/JavaAppiumAutomation2/apks/Wikipedia.app");
    DesiredCapabilities.setCapability("platformName", "iOS");
    DesiredCapabilities.setCapability("platformVersion", "11.4");
    DesiredCapabilities.setCapability("deviceName", "iPhone SE");
    //DesiredCapabilities.setCapability("fullReset", "true");
    DesiredCapabilities.setCapability("noReset", "true");
    return DesiredCapabilities;
  }


  private boolean isPlatform(String my_platform) {
    String platform = this.getPlatformVar();
    return my_platform.equals(platform);
  }


  private String getPlatformVar() {
    return System.getenv("PLATFORM");
  }


}
