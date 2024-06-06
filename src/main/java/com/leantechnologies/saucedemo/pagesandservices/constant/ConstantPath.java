package com.leantechnologies.saucedemo.pagesandservices.constant;

public class ConstantPath {

    /**
     * Selenium Configs
     */
    public static final String CHROMEDRIVER = "webdriver.chrome.driver";
    public static final String CHROMEDRIVEREXE = ".\\src\\test\\resources\\chromedriver.exe";
    public static final int AVGWAIT = 30;

    /**
     * Test Data Configs
     */
    public static final String TESTDATA = "./src/test/resources/testdata";

    /**
     * Pages Locator Files
     */
    private static final String BASEDIR = ".\\src\\main\\resources\\pagesElementProperties\\";
    public static final String PRODUCTSPAGE = BASEDIR + "productsPage.properties";
    public static final String YOURCARTPAGE = BASEDIR + "yourCartPage.properties";
    public static final String CHECKOUTPAGE = BASEDIR + "checkoutPage.properties";
    public static final String LOGINPAGE = BASEDIR + "loginPage.properties";
    public static final String CHECKOUTOVERVIEWPAGE = BASEDIR + "checkoutOverviewPage.properties";
    public static final String CHECKOUTCOMPLETEPAGE = BASEDIR + "checkoutCompletePage.properties";

    /*
     * Log4j Config Properties file
     */
    public static final String LOG4JPROP = ".\\src\\main\\resources\\log4j.properties";

    /*
     * Failed Test Screenshots
     */
    public static final String SCREENSHOT = ".\\src\\test\\resources\\screenshots\\";
    public static final String FILEEXT = "png";

    /*
     * Extent Report location
     */
    public static final String EXTENTREPORT = ".\\src\\test\\resources\\reports\\";

}
