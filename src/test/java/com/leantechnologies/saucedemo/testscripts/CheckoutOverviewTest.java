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
import java.util.Map;

public class CheckoutOverviewTest extends TestBase {

    private ExtentTest test;
    private PredefinedActions predefinedActions;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutPage checkoutPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    @BeforeMethod
    public void beforeMethod(Method method) {
        predefinedActions = new PredefinedActions();
        predefinedActions.start();
        loginPage = LoginPage.getPageInstance();
        productsPage = ProductsPage.getPageInstance();
        yourCartPage = YourCartPage.getPageInstance();
        checkoutPage = CheckoutPage.getPageInstance();
        checkoutOverviewPage = CheckoutOverviewPage.getPageInstance();
        checkoutCompletePage = CheckoutCompletePage.getPageInstance();
        test = extent.createTest(method.getName());
        test.info("Test Description: " + method.getName());
        login(); // login step executed before every test method is executed
        addProductsInCart(); // add products step executed before every test method is executed
        navigateToYourCartPage(); // navigate to your cart page executed before every test method is executed
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_items_added(String firstName, String lastName, String postalCode) {

        test.info("STEP 4: Get list of products from Your Cart page");
        ArrayList<String> listOfProductsInCart = yourCartPage.getListOfProductsAlreadyAddedInCart();

        test.info("STEP 5: Click on Checkout button on Your Cart page");
        yourCartPage.clickOnCheckoutButon();

        test.info("STEP 6: Fill in the details on Checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        test.info("STEP 7: Click on Continue button on Checkout page");
        checkoutPage.clickOnContinueButton();

        test.info("STEP 8: Get list of products from Checkout Overview page");
        ArrayList<String> listOfProductsOnCheckoutOverviewPage = checkoutOverviewPage.getListOfProductsAlreadyAddedInCartAndPresentOnCheckoutOverviewPage();

        test.info("STEP 9: Verify that the products added in the cart page match the products present in the checkout overview page");
        boolean isEqual = listOfProductsOnCheckoutOverviewPage.containsAll(listOfProductsInCart) && listOfProductsInCart.containsAll(listOfProductsOnCheckoutOverviewPage);
        Assert.assertTrue(isEqual, "true");
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_items_amount_total_with_your_cart(String firstName, String lastName, String postalCode) {

        // STEP 4: Get the total amount of items in the cart on the Your Cart page
        double totalAmountOnYourCartPage = yourCartPage.getAmountTotalOfYourCart();
        test.info("STEP 4: Get the total PriceAmount of items in the cart on the Your Cart page: " + totalAmountOnYourCartPage);

        // STEP 5: Click on the Checkout button on the Your Cart page
        test.info("STEP 5: Click on the Checkout button on the Your Cart page");
        yourCartPage.clickOnCheckoutButon();

        // STEP 6: Fill in the required details on the Checkout page
        test.info("STEP 6: Fill in the required details on the Checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        // STEP 7: Click on the Continue button on the Checkout page
        test.info("STEP 7: Click on the Continue button on the Checkout page");
        checkoutPage.clickOnContinueButton();

        // STEP 8: Get the total price amount of items in the cart on the Checkout Overview page
        double totalAmountOnCheckoutOverviewPage = checkoutOverviewPage.getAmountTotalOnCheckoutOverviewPage();
        test.info("STEP 8: Get the total Price amount of items in the cart on the Checkout Overview page: " + totalAmountOnCheckoutOverviewPage);

        // STEP 9: Verify that the total amount of items in the cart on the Your Cart page matches the total amount on the Checkout Overview page
        test.info("STEP 9: Verify that the total amount of items in the cart on the Your Cart page matches the total amount on the Checkout Overview page");
        Assert.assertEquals(totalAmountOnYourCartPage, totalAmountOnCheckoutOverviewPage, "The total price amount of items in the cart on the Your Cart page does not match the price total amount on the Checkout Overview page");
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_items_amount_total_on_same_page(String firstName, String lastName, String postalCode) {

        // STEP 4: Click on the Checkout button on the Your Cart page
        test.info("STEP 4: Click on the Checkout button on the Your Cart page");
        yourCartPage.clickOnCheckoutButon();

        // STEP 5: Fill in the required details on the Checkout page
        test.info("STEP 5: Fill in the required details on the Checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        // STEP 6: Click on the Continue button on the Checkout page
        test.info("STEP 6: Click on the Continue button on the Checkout page");
        checkoutPage.clickOnContinueButton();

        double amountBelowPriceTotalCheckoutOverviewPage = checkoutOverviewPage.getAmountBelowPriceTotalCheckoutOverviewPage();
        double totalAmountOnCheckoutOverviewPage = checkoutOverviewPage.getAmountTotalOnCheckoutOverviewPage();

        // STEP 7: Verify that the total price of items(after individual addition) on Checkout Overview page matches the total amount on the Checkout Overview page
        test.info("STEP 7: Verify that the total price of items(after individual addition) on Checkout Overview page matches the total amount on the Checkout Overview page");
        Assert.assertEquals(totalAmountOnCheckoutOverviewPage, amountBelowPriceTotalCheckoutOverviewPage);
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_items_amount_total_with_tax(String firstName, String lastName, String postalCode) {

        // STEP 4: Click on the Checkout button on the Your Cart page
        test.info("STEP 4: Click on the Checkout button on the Your Cart page");
        yourCartPage.clickOnCheckoutButon();

        // STEP 5: Fill in the required details on the Checkout page
        test.info("STEP 5: Fill in the required details on the Checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        // STEP 6: Click on the Continue button on the Checkout page
        test.info("STEP 6: Click on the Continue button on the Checkout page");
        checkoutPage.clickOnContinueButton();

        double totalAmountOnCheckoutOverviewPage = checkoutOverviewPage.getAmountTotalOnCheckoutOverviewPage();
        double taxAmount = checkoutOverviewPage.getTaxAmount();
        double finalTotalFromCheckoutOverviewPage = checkoutOverviewPage.getFinalTotal();
        double finalAmountAfterAdditionOfTaxAndFinalAmount = totalAmountOnCheckoutOverviewPage + taxAmount;

        // STEP 7: Verify that the total price of items after tax addition on Checkout Overview page matches the final total amount displayed on the Checkout Overview page
        test.info("STEP 7: Verify that the total price of items after tax addition on Checkout Overview page matches the final total amount displayed on the Checkout Overview page");
        Assert.assertEquals(finalAmountAfterAdditionOfTaxAndFinalAmount, finalTotalFromCheckoutOverviewPage);
    }

    @Test(dataProvider = "csvDataProvider", dataProviderClass = TestDataProvider.class)
    public void verify_checkout_overview_page_finish_button(String firstName, String lastName, String postalCode) {

        // STEP 4: Click on the Checkout button on the Your Cart page
        test.info("STEP 4: Click on the Checkout button on the Your Cart page");
        yourCartPage.clickOnCheckoutButon();

        // STEP 5: Fill in the required details on the Checkout page
        test.info("STEP 5: Fill in the required details on the Checkout page");
        Map<String, String> mapOfDetails = new HashMap<>();
        mapOfDetails.put("firstname", firstName);
        mapOfDetails.put("lastname", lastName);
        mapOfDetails.put("postalcode", postalCode);
        checkoutPage.fillDetailsOnCheckoutPage(mapOfDetails);

        // STEP 6: Click on the Continue button on the Checkout page
        test.info("STEP 6: Click on the Continue button on the Checkout page");
        checkoutPage.clickOnContinueButton();

        // STEP 7: Click on the Finish button on the Checkout Overview page
        test.info("STEP 7: Click on the Finish button on the Checkout Overview page");
        checkoutOverviewPage.clickOnFinishButton();

        // STEP 8: Verify Checkout:Complete Page is displayed
        test.info("STEP 8: Verify Checkout:Complete Page is displayed");
        String checkoutCompletePageUrl = checkoutCompletePage.isCheckoutCompletePageDisplayed();
        Assert.assertEquals(checkoutCompletePageUrl, "https://www.saucedemo.com/checkout-complete.html");

        // STEP 9: Verify Order is Successfully Placed
        test.info("STEP 9: Verify Order is Successfully Placed");
        String successMessage = checkoutCompletePage.verifyOrderIsSuccessfullyPlaced();
        Assert.assertEquals(successMessage, "Thank you for your order!");
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

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped");
        } else {
            test.log(Status.PASS, "Test passed");
        }
        checkoutOverviewPage.tearDown();
        extent.flush();
    }


}