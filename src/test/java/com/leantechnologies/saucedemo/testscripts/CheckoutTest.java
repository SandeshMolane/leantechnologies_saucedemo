package com.leantechnologies.saucedemo.testscripts;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.pages.*;
import com.leantechnologies.saucedemo.pagesandservices.utils.CredentialsUtil;
import com.leantechnologies.saucedemo.pagesandservices.utils.TestDataProvider;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutTest extends TestBase {

    private ExtentTest test;
    private PredefinedActions predefinedActions;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage checkoutOverviewPage;

    @BeforeMethod
    public void beforeMethod(Method method) {
        predefinedActions = new PredefinedActions();
        predefinedActions.start();
        loginPage = LoginPage.getPageInstance();
        productsPage = ProductsPage.getPageInstance();
        yourCartPage = YourCartPage.getPageInstance();
        checkoutPage = CheckoutPage.getPageInstance();
        checkoutOverviewPage = CheckoutOverviewPage.getPageInstance();
        test = extent.createTest(method.getName());
        test.info("Test Description: " + method.getName());
        login(); // login step executed before every test method is executed
        addProductsInCart(); // add products step executed before every test method is executed
        navigateToYourCartPage(); // navigate to your cart page executed before every test method is executed
        navigateToCheckoutPage(); // navigate to checkout page executed before every test method is executed
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_details_added_in_checkout_page_match_with_input_values(String firstName, String lastName, String postalCode) {

        // STEP 5: Fill in the details on checkout page
        test.info("STEP 5: Fill in the details on checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        List<String> listOfValuesAdded = new ArrayList<>();
        for (Map.Entry<String, String> entry : mapOfDetails.entrySet()) {
            listOfValuesAdded.add(entry.getValue());
        }

        // STEP 6: Verify that values are added in fields
        test.info("STEP 7: Verify that values are added in fields");
        List<String> listOfValuesFetchedAfterAdding = checkoutPage.getDetailsAddedInFields("firstname", "lastname", "postalcode");
        boolean isEqual = listOfValuesAdded.containsAll(listOfValuesFetchedAfterAdding) && listOfValuesFetchedAfterAdding.containsAll(listOfValuesAdded);
        Assert.assertTrue(isEqual, "Expected the values added on Checkout page to match, but they were not the same");
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_displayed(String firstName, String lastName, String postalCode) {
        // STEP 5: Fill in the details on checkout page
        test.info("STEP 5: Fill in the details on checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        // STEP 6: Click on continue button
        test.info("STEP 6: Click on continue button");
        checkoutPage.clickOnContinueButton();

        // STEP 7: Verify that checkout overview page is displayed
        test.info("STEP 7: Verify that checkout overview page is displayed");
        String output1 = checkoutOverviewPage.isCheckoutOverviewPageDisplayed();
        Assert.assertEquals(output1, "https://www.saucedemo.com/checkout-step-two.html");
    }

    private void login() {
        test.info("STEP 1: Login to the application");
        loginPage.login(CredentialsUtil.getUsername(), CredentialsUtil.getPassword());
    }

    private void addProductsInCart() {
        test.info("STEP 2: Add three products in your cart");
        productsPage.addProductsToCart("Backpack", "Bike Light", "Bolt T-Shirt");
    }

    private void navigateToYourCartPage() {
        // STEP 3: Navigate to the cart page
        test.info("STEP 3: Navigate to the cart page");
        productsPage.clickOnCartIcon();
    }

    private void navigateToCheckoutPage() {
        // STEP 4: Click on the checkout button
        test.info("STEP 4: Click on the checkout button");
        yourCartPage.clickOnCheckoutButon();
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
        checkoutPage.tearDown();
        extent.flush();
    }

}