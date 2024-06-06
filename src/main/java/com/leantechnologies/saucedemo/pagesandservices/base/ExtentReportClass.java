package com.leantechnologies.saucedemo.pagesandservices.base;

import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;

public final class ExtentReportClass {

    public static ExtentReports extent;
    public static ExtentTest test;

    public static void initReport() {
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter(ConstantPath.EXTENTREPORT + "extent-report.html");
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setReportName("Extent Report SauceDemo Practice");
        spark.config().setDocumentTitle("SauceDemo Extent Report");
        extent.attachReporter(spark);
    }

    public static void flushReports() {
        extent.flush();
    }

    public static void createTest(String testcaseName) {
        test = extent.createTest(testcaseName);
        test.createNode("Test Case : " + testcaseName);
    }

    public static void pass(ITestResult result) {
        test.log(Status.PASS, "Test case passed : " + result.getMethod().getMethodName());
    }

    public static void fail(ITestResult result) {
        test.addScreenCaptureFromBase64String(PredefinedActions.takeScreenShotAsBase64(result.getMethod().getMethodName()),result.getMethod().getMethodName());
        test.log(Status.FAIL, result.getThrowable());
    }

    public static void skip(ITestResult result) {
        test.log(Status.SKIP, result.getThrowable());
    }
}


