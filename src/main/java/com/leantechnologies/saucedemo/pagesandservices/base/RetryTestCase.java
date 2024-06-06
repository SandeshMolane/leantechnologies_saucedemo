package com.leantechnologies.saucedemo.pagesandservices.base;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestCase implements IRetryAnalyzer {

	private int maxRetry = 2;
	private int tryCnt = 0;

	@Override
	public boolean retry(ITestResult result) {
			if (tryCnt < maxRetry) {
				tryCnt++;
				return true;
			} else
				return false;
		}
}
