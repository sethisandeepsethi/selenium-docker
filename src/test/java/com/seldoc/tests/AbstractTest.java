package com.seldoc.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.seldoc.listener.TestListener;
import com.seldoc.util.Config;
import com.seldoc.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners({TestListener.class})
public abstract class AbstractTest {

    protected WebDriver driver;
    public static Logger log = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeSuite
    public void initializeConfigs(){
        Config.initialize();
    }

    @BeforeTest
    public void setDriver(ITestContext testContext) throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constants.IS_REMOTE)) ? getRemoteDriver() : getLocalDriver();
        testContext.setAttribute(Constants.CURRENT_DRIVER, this.driver);
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = new ChromeOptions();

         if (Config.get(Constants.BROWSER).equalsIgnoreCase(Constants.FIREFOX))
           capabilities = new FirefoxOptions();

         String urlFormat = Config.get(Constants.REMOTE_URL_FORMAT);
         String hubHost = Config.get(Constants.REMOTE_URL_HOST);
         String url = String.format(urlFormat, hubHost);
        return new RemoteWebDriver(new URL(url), capabilities);
    }

    private WebDriver getLocalDriver() {
        if (Config.get(Constants.BROWSER).equalsIgnoreCase(Constants.FIREFOX)) {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        }

        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void quitDriver(){
        this.driver.quit();
    }

    @AfterMethod
    public void sleep(){
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
    }

}
