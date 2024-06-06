package com.leantechnologies.saucedemo.pagesandservices.base;

import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.DateAndTimeOperation;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class PredefinedActions {
    protected static WebDriver driver;
    static private WebDriverWait wait;

    public void start() {
        start("https://www.saucedemo.com");
    }

    public void start(String url) {
        System.setProperty(ConstantPath.CHROMEDRIVER, ConstantPath.CHROMEDRIVEREXE);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        wait = new WebDriverWait(driver, ConstantPath.AVGWAIT);
    }

    public void start(String browserName, String url) {

        switch(browserName) {
            case "chrome" :
                System.setProperty(ConstantPath.CHROMEDRIVER, ConstantPath.CHROMEDRIVEREXE);
                driver = new ChromeDriver();
                break;
            case "ie" :
                driver = new InternetExplorerDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.get(url);
        wait = new WebDriverWait(driver, ConstantPath.AVGWAIT);
    }

    private String getLocatorType(String locator) {
        String[] arr = locator.split("]:-");
        String locatorType = arr[0].substring(1);
        return locatorType;
    }

    private String getLocatorValue(String locator) {
        String[] arr = locator.split("]:-");
        return arr[1].trim();
    }

    protected WebElement getElement(String locator, boolean isWaitRequired) {
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);
        WebElement element;
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
                else
                    element = driver.findElement(By.xpath(locatorValue));
                break;

            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
                else
                    element = driver.findElement(By.id(locatorValue));
                break;

            case "LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
                else
                    element = driver.findElement(By.linkText(locatorValue));
                break;

            case "CSS":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
                else
                    element = driver.findElement(By.cssSelector(locatorValue));
                break;

            default:
                element = null;
                System.out.println("Invalid LocatorType");
        }
        return element;
    }

    protected WebElement getElement(String locatorType, String locator, boolean isWaitRequired) {
        WebElement element;
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                else
                    element = driver.findElement(By.xpath(locator));
                break;

            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                else
                    element = driver.findElement(By.id(locator));
                break;

            case "LINKTEXT":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locator)));
                else
                    element = driver.findElement(By.linkText(locator));
                break;

            default:
                element = null;
                System.out.println("Invalid LocatorType");
        }
        return element;
    }

    protected void moveToElement(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }

    protected WebElement getElementInsideElement(WebElement parentElement, String parentLocator, String locatorType,
                                                 String locator, boolean isWaitRequired) {
        WebElement element;
        boolean flag = false;
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired) {
                    element = wait
                            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(parentLocator + "/" + locator)));
                    flag = wait.until(ExpectedConditions.textMatches(By.xpath(parentLocator + "/" + locator),
                            Pattern.compile("^[^0]")));
                } else
                    element = parentElement.findElement(By.xpath(locator));
                break;

            default:
                element = null;
                System.out.println("Invalid LocatorType");
        }
        System.out.println(flag);
        return element;
    }

    protected List<WebElement> getAllElements(String locator, boolean isWaitRequired) {
        List<WebElement> element;
        String locatorType = getLocatorType(locator);
        String locatorValue = getLocatorValue(locator);

        switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locatorValue)));
                else
                    element = driver.findElements(By.xpath(locatorValue));
                break;

            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locatorValue)));
                else
                    element = driver.findElements(By.id(locatorValue));
                break;

            default:
                element = null;
                System.out.println("Invalid LocatorType");
        }
        return element;
    }

    protected List<WebElement> getAllElements(String locatorType, String locator, boolean isWaitRequired) {
        List<WebElement> element;
        switch (locatorType.toUpperCase()) {
            case "XPATH":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
                else
                    element = driver.findElements(By.xpath(locator));
                break;

            case "ID":
                if (isWaitRequired)
                    element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locator)));
                else
                    element = driver.findElements(By.id(locator));
                break;

            default:
                element = null;
                System.out.println("Invalid LocatorType");
        }
        return element;
    }

    protected void scrollToElement(WebElement element) {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    protected boolean isElementDisplayed(WebElement element) {
        if (element.isDisplayed()) {
            return true;
        } else {
            scrollToElement(element);
            return element.isDisplayed();
        }
    }

    protected boolean isElementDisplayed(String locator) {
        try {
            WebElement element = getElement(locator, true);
            return isElementDisplayed(element);
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(String locatorType, String locator) {
        try {
            WebElement element = getElement(locatorType, locator, true);
            return isElementDisplayed(element);
        } catch (Exception e) {
            return false;
        }
    }

    protected String getElementText(WebElement element) {
        scrollToElement(element);
        return element.getText();
    }

    protected String getElementText(String locatorType, String locator) {
        return getElement(locatorType, locator, true).getText();
    }

    protected String getElementText(String locator) {
        return getElement(locator, true).getText();
    }

    protected void enterText(String locator, String text) {
        WebElement element = getElement(locator, true);
        if (element.isEnabled())
            element.sendKeys(text);
    }

    protected void clickOnElement(String locator) {
        WebElement element = getElement(locator, true);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void clickOnElement(String locatorType, String locator) {
        WebElement element = getElement(locatorType, locator, true);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected String getElementAttribute(String locatorType, String locator, String attribute) {
        WebElement element = getElement(locatorType, locator, true);
        return element.getAttribute(attribute);
    }

    protected String getElementAttribute(String locator, String attribute) {
        WebElement element = getElement(locator, true);
        return element.getAttribute(attribute);
    }

    protected String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }

    protected void staticWait(int waitTime) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentPageTitle() {
        return driver.getTitle();
    }

    static protected void takeScreenShot(String fileName){
        TakesScreenshot ts = (TakesScreenshot)driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(srcFile, new File(DateAndTimeOperation.getFileFormattedName(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static protected String takeScreenShotAsBase64(String fileName){
        TakesScreenshot ts = (TakesScreenshot)driver;
        String output =  ts.getScreenshotAs(OutputType.BASE64);
        return output;
    }

}