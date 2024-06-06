package com.leantechnologies.saucedemo.testscripts;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.leantechnologies.saucedemo.pagesandservices.pages.LoginPage;
import com.leantechnologies.saucedemo.pagesandservices.pages.ProductsPage;
import java.util.ArrayList;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.markuputils.ExtentColor;
import java.lang.reflect.Method;


public class ProductsTest extends TestBase {
    private ExtentTest test;
    private PredefinedActions predefinedActions;
    private ProductsPage productsPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod(Method method) {
        predefinedActions = new PredefinedActions();
        predefinedActions.start();
        loginPage = LoginPage.getPageInstance();
        productsPage = ProductsPage.getPageInstance();
        test = extent.createTest(method.getName());
        test.info("Test Description: " + method.getName());
        login(); // login step executed before every test method is executed
    }

    @Test
    public void verify_products_are_added_in_cart() {

        test.info("STEP 2: Add products to the cart");
        productsPage.addProductsToCart("Backpack", "Bike Light", "Bolt T-Shirt");

        test.info("STEP 3: Verify that the products are added to the cart");
        Assert.assertEquals(productsPage.verifyProductsAddedInCart(), "3", "Products count in cart does not match");
        test.log(Status.PASS, MarkupHelper.createLabel("Products count in cart matches", ExtentColor.GREEN));
    }

    @Test
    public void verify_count_of_products_to_be_added_in_cart() {

        test.info("STEP 2: Add products to the cart");
        productsPage.addProductsToCart("Backpack", "Bike Light", "Bolt T-Shirt");

        test.info("STEP 3: Get the list of products to be added to the cart");
        ArrayList<String> listOfProducts = productsPage.getListOfProductsToBeAddedInCart();

        test.info("STEP 4: Verify the count of products to be added to the cart");
        Assert.assertEquals(listOfProducts.size(), 3, "The count of products to be added to the cart is not correct");
    }

    @Test
    public void verify_your_cart_page_is_displayed_on_clicking() {

        test.info("STEP 2: Add products to the cart");
        productsPage.addProductsToCart("Backpack", "Bike Light", "Bolt T-Shirt");

        // STEP2: Add products to the cart and navigate to the cart page
        test.info("STEP 3: Navigate to the cart page");
        productsPage.clickOnCartIcon();

        // STEP3: Verify that the "Your Cart" page is displayed
        test.info("STEP 4: Your Cart Page is displayed");
        Assert.assertEquals(productsPage.verifyYourCartPage(), "Your Cart");
    }

    private void login() {
        test.info("STEP 1: Login to the application");
        loginPage.login(CredentialsUtil.getUsername(), CredentialsUtil.getPassword());
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
        productsPage.tearDown();
        extent.flush();
    }

}
