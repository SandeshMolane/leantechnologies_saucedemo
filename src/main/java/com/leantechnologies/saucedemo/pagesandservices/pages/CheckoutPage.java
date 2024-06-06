package com.leantechnologies.saucedemo.pagesandservices.pages;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class CheckoutPage extends PredefinedActions {

    private static CheckoutPage checkoutPage;
    private PropertiesFileOperation properties;

    private CheckoutPage() {
        properties = new PropertiesFileOperation(ConstantPath.CHECKOUTPAGE);
    }

    public static CheckoutPage getPageInstance() {
        if (checkoutPage == null)
            checkoutPage = new CheckoutPage();
        return checkoutPage;
    }

    public String isChckoutPageDisplayed() {
        return getCurrentPageUrl();
    }

    public void fillDetailsOnCheckoutPage(Map<String, String> valuesToBeFilled) {
        for (Map.Entry<String, String> entry : valuesToBeFilled.entrySet()) {
            String key = entry.getKey().toLowerCase();
            String value = entry.getValue();
            switch (key) {
                case "firstname":
                    enterText(properties.getValue("checkoutPageFirstNameXPath"), value);
                    break;
                case "lastname":
                    enterText(properties.getValue("checkoutPageLastName"), value);
                    break;
                case "postalcode":
                    enterText(properties.getValue("checkoutPagePostalCode"), value);
                    break;
                // Add more cases as needed
                default:
                    throw new IllegalArgumentException("Invalid key: " + key);
            }
        }
    }

    public void clickOnContinueButton() {
        clickOnElement(properties.getValue("checkoutPageContinueButton"));
    }

    public List<String> getDetailsAddedInFields(String... namesOfFields) {
        List<String> listOfValuesEntered = new ArrayList<>();
        for (String eachField : namesOfFields) {
            switch (eachField) {
                case "firstname":
                    listOfValuesEntered.add(getElementAttribute(properties.getValue("checkoutPageFirstName"), "value"));
                    break;

                case "lastname":
                    listOfValuesEntered.add(getElementAttribute(properties.getValue("checkoutPageLastName"), "value"));
                    break;
                case "postalcode":
                    listOfValuesEntered.add(getElementAttribute(properties.getValue("checkoutPagePostalCode"), "value"));
                    break;
                // Add more cases as needed
                default:
                    throw new IllegalArgumentException("Invalid key: " + eachField);
            }
        }
        return listOfValuesEntered;
    }

    public void tearDown() {
        PredefinedActions.driver.quit();
    }

}