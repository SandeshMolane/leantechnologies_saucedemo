package com.leantechnologies.saucedemo.pagesandservices.base;

import com.google.common.base.Strings;
import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ITestListenerImpl extends PredefinedActions implements ITestListener, IAnnotationTransformer {
	String defaultBrowser = "chrome";
	@Override
	public void onTestStart(ITestResult result) {
		String bName = Strings.isNullOrEmpty(System.getProperty("browserName"))
				? defaultBrowser : System.getProperty("browserName");
		start(bName, "https://www.saucedemo.com/checkout-complete.html");
		ExtentReportClass.createTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReportClass.pass(result);
		tearDown();
	}

	private void tearDown() {

	}

	@Override
	public void onTestFailure(ITestResult result) {
		takeScreenShot(result.getMethod().getMethodName());	
		ExtentReportClass.fail(result);
		tearDown();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportClass.skip(result);
		tearDown();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		// param from cmd
		ExtentReportClass.initReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportClass.flushReports();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryTestCase.class);
	}

}
