package com.leantechnologies.saucedemo.pagesandservices.pages;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;

public class CheckoutCompletePage extends PredefinedActions {

    private static CheckoutCompletePage checkoutCompletePage;
    private PropertiesFileOperation properties;

    private CheckoutCompletePage() {
        properties = new PropertiesFileOperation(ConstantPath.CHECKOUTCOMPLETEPAGE);
    }

    public static CheckoutCompletePage getPageInstance() {
        if (checkoutCompletePage == null)
            checkoutCompletePage = new CheckoutCompletePage();
        return checkoutCompletePage;
    }

    public String isCheckoutCompletePageDisplayed() {
        return getCurrentPageUrl();
    }

    public String verifyOrderIsSuccessfullyPlaced() {

        return getElementText(properties.getValue("checkoutCompletePageMessage"));
    }
}