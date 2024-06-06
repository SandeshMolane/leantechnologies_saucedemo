package com.leantechnologies.saucedemo.testscripts;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.pages.ProductsPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.leantechnologies.saucedemo.pagesandservices.pages.LoginPage;
import static com.leantechnologies.saucedemo.pagesandservices.utils.CredentialsUtil.*;
import com.aventstack.extentreports.ExtentTest;

public final class LoginTest extends TestBase {
    private LoginPage loginPage;
    private ExtentTest test;
    private PredefinedActions predefinedActions;

    @BeforeMethod
    public void beforeMethod() {
        test = extent.createTest("verify_user_able_to_login");
        predefinedActions = new PredefinedActions();
        predefinedActions.start();
        loginPage = LoginPage.getPageInstance();
    }

    @Test
    public void verify_user_able_to_login() {
        test.info("STEP 1: Login to the application");
        LoginPage.getPageInstance().login(getUsername(), getPassword());

        test.info("STEP 2: Verify Login page logo is displayed");
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Login page logo is not displayed");

        test.info("STEP 3: Verify Products page is displayed");
        ProductsPage productsPage = ProductsPage.getPageInstance();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Products page is not displayed");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped");
        } else {
            test.log(Status.PASS, "Test passed");
        }
        loginPage.tearDown();
        extent.flush();
    }


}