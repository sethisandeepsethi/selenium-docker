package com.seldoc.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.seldoc.listener.TestListener;
import com.seldoc.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners({TestListener.class})
public abstract class AbstractTest {

    protected WebDriver driver;

    @BeforeTest
    public void setDriver(ITestContext testContext) throws MalformedURLException {
        System.out.println("isRemote: " +Boolean.getBoolean("isRemote"));
        System.out.println("browser: " + System.getProperty("browser"));

        this.driver = Boolean.getBoolean("isRemote") ? getRemoteDriver() : getLocalDriver();
        testContext.setAttribute(Constants.CURRENT_DRIVER, this.driver);
    }

    private WebDriver getRemoteDriver() throws MalformedURLException {
        Capabilities capabilities = null;
        capabilities = new ChromeOptions();

         if (System.getProperty("browser").equalsIgnoreCase("firefox")) {
           capabilities = new FirefoxOptions();
        }
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
    }

    private WebDriver getLocalDriver() {
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
