package com.leantechnologies.saucedemo.pagesandservices.pages;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;
import java.util.ArrayList;

public class CheckoutOverviewPage extends PredefinedActions {

    private static CheckoutOverviewPage checkoutOverviewPage;
    private PropertiesFileOperation properties;

    private CheckoutOverviewPage() {
        properties = new PropertiesFileOperation(ConstantPath.CHECKOUTOVERVIEWPAGE);
    }

    public static CheckoutOverviewPage getPageInstance() {
        if (checkoutOverviewPage == null)
            checkoutOverviewPage = new CheckoutOverviewPage();
        return checkoutOverviewPage;
    }

    public String isCheckoutOverviewPageDisplayed() {
        return getCurrentPageUrl();
    }

    public ArrayList<String> getListOfProductsAlreadyAddedInCartAndPresentOnCheckoutOverviewPage() {
        ArrayList<String> listOfProductsAdded = new ArrayList<>();
        listOfProductsAdded.add(getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBackPack")));
        listOfProductsAdded.add(getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBikeLight")));
        listOfProductsAdded.add(getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBoltTShirt")));

        return listOfProductsAdded;
    }

    public Double getAmountTotalOnCheckoutOverviewPage() {
        String backPackPrice = getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBackPackPrice"));
        double backPackPriceWithoutDollarSign = Double.parseDouble(backPackPrice.replace("$", ""));
        String bikeLightPrice = getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBikeLightPrice"));
        double bikeLightPriceWithoutDollarSign = Double.parseDouble(bikeLightPrice.replace("$", ""));
        String boltTShirtPrice = getElementText(properties.getValue("checkoutOverviewPageItemSauceLabsBoltTShirtPrice"));
        double boltTShirtPriceWithoutDollarSign = Double.parseDouble(boltTShirtPrice.replace("$", ""));

        return backPackPriceWithoutDollarSign+bikeLightPriceWithoutDollarSign+boltTShirtPriceWithoutDollarSign;
    }

    public Double getAmountBelowPriceTotalCheckoutOverviewPage() {
        String itemTotalBelowPriceTotal = getElementText(properties.getValue("checkoutOverviewPageItemTotalBelowPriceTotal"));
        String[] splitOnDollarPrice = itemTotalBelowPriceTotal.split(" ");

        return Double.parseDouble(splitOnDollarPrice[2].replace("$", ""));
    }

    public Double getTaxAmount() {
        String itemTaxAmountBelowPriceTotal = getElementText(properties.getValue("checkoutOverviewPageItemTaxAmountBelowPriceTotal"));
        String[] splitOnDollarPrice = itemTaxAmountBelowPriceTotal.split(" ");

        return Double.parseDouble(splitOnDollarPrice[1].replace("$", ""));
    }

    public Double getFinalTotal() {
        String finalAmount = getElementText(properties.getValue("checkoutOverviewPageItemTotalAmount"));
        String[] splitOnDollarPrice = finalAmount.split(" ");

        return Double.parseDouble(splitOnDollarPrice[1].replace("$", ""));
    }

    public void clickOnFinishButton() {
        clickOnElement(properties.getValue("checkoutOverviewPageFinishButton"));
    }

    public void tearDown() {
        PredefinedActions.driver.quit();
    }
}