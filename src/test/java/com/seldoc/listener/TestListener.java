package com.seldoc.listener;

import com.seldoc.util.Constants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        TakesScreenshot screenshot = (TakesScreenshot) result.getTestContext().getAttribute(Constants.CURRENT_DRIVER);
        String ss = screenshot.getScreenshotAs(OutputType.BASE64 );
        String htmlImageFormat = "<img width=700px src='data:image/png;base64,%s' />";
        String htmlImage = String.format(htmlImageFormat,ss);
        Reporter.log(htmlImage);
    }
}
