package com.leantechnologies.saucedemo.testscripts;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.leantechnologies.saucedemo.pagesandservices.pages.CheckoutPage;
import com.leantechnologies.saucedemo.pagesandservices.pages.LoginPage;
import com.leantechnologies.saucedemo.pagesandservices.pages.ProductsPage;
import com.leantechnologies.saucedemo.pagesandservices.pages.YourCartPage;
import com.leantechnologies.saucedemo.pagesandservices.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class YourCartTest extends TestBase {
    private ExtentTest test;
    private PredefinedActions predefinedActions;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private YourCartPage yourCartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void beforeMethod(Method method) {
        predefinedActions = new PredefinedActions();
        predefinedActions.start();
        loginPage = LoginPage.getPageInstance();
        productsPage = ProductsPage.getPageInstance();
        yourCartPage = YourCartPage.getPageInstance();
        checkoutPage = CheckoutPage.getPageInstance();
        test = extent.createTest(method.getName());
        test.info("Test Description: " + method.getName());
        login(); // login step executed before every test method is executed
        addProductsInCart(); // add products step executed before every test method is executed
    }

    @Test
    public void verify_items_added_in_your_cart_page_match_items_added_in_products_page() {

        // STEP 3: Navigate to the cart page and get the list of products
        test.info("STEP 3: Navigate to the cart page and get the list of products");
        ArrayList<String> listOfProductsOnProductsPage = productsPage.getListOfProductsToBeAddedInCart();
        productsPage.clickOnCartIcon();

        // STEP 4: Verify that the products added in the cart page match the products added in the products page
        test.info("STEP 4: Verify that the products added in the cart page match the products added in the products page");
        ArrayList<String> listOfProductsInCart = yourCartPage.getListOfProductsAlreadyAddedInCart();
        boolean isEqual = listOfProductsOnProductsPage.containsAll(listOfProductsInCart) && listOfProductsInCart.containsAll(listOfProductsOnProductsPage);
        Assert.assertTrue(isEqual, "Expected the products added in the cart page to match the products added in the products page, but they were not the same");
    }

    @Test
    public void verify_your_cart_page_items_amount_total() {

        // STEP 3: Get the total amount of products on the products page
        test.info("STEP 3: Get the total amount of products on the products page");
        double totalAmountOnProductsPage = productsPage.getAmountTotalOfProductsToBeAddedInCart();

        // STEP 4: Navigate to the cart page and verify the total amount of products
        test.info("STEP 4: Navigate to the cart page and verify the total amount of products");
        productsPage.clickOnCartIcon();
        double totalAmountOnYourCartPage = yourCartPage.getAmountTotalOfYourCart();
        Assert.assertEquals(totalAmountOnProductsPage, totalAmountOnYourCartPage, "The total amount of products on the products page and the cart page do not match.");
    }

    @Test
    public void verify_checkout_button_working_on_your_cart() {

        // STEP 3: Navigate to the cart page
        test.info("STEP 3: Navigate to the cart page");
        productsPage.clickOnCartIcon();

        // STEP 4: Click on the checkout button
        test.info("STEP 4: Click on the checkout button");
        yourCartPage.clickOnCheckoutButon();

        // STEP 5: Verify that the checkout page is displayed
        test.info("STEP 5: Verify that the checkout page is displayed");
        String output = checkoutPage.isChckoutPageDisplayed();
        System.out.println("Checkout page ka title " + output);
        Assert.assertEquals(output, "https://www.saucedemo.com/checkout-step-one.html");
    }

    private void login() {
        test.info("STEP 1: Login to the application");
        loginPage.login(CredentialsUtil.getUsername(), CredentialsUtil.getPassword());
    }

    private void addProductsInCart() {
        test.info("STEP 2: Add three products in your cart");
        productsPage.addProductsToCart("Backpack", "Bike Light", "Bolt T-Shirt");
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
        yourCartPage.tearDown();
        extent.flush();
    }

}