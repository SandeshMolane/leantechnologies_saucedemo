package com.leantechnologies.saucedemo.testscripts;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.pages.LoginPage;

public class TestBase {

    public static ExtentReports extent;

    static {
        String logFilename = System.getProperty("xmlsuite");
        System.setProperty("log.file", logFilename+".log");
    }

    @BeforeSuite
    public void beforeSuite() {
        PropertyConfigurator.configure(ConstantPath.LOG4JPROP);
        // Initialize the Extent report
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("src/test/resources/reports");
        extent.attachReporter(spark);
    }

    @AfterSuite
    public void afterSuite() {
        // Close the Extent report
        extent.flush();
    }



}
