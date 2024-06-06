package com.leantechnologies.saucedemo.pagesandservices.pages;

import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;
import java.util.ArrayList;

public class YourCartPage extends PredefinedActions {

    private static YourCartPage yourCartPage;
    private PropertiesFileOperation properties;

    private YourCartPage() {
        properties = new PropertiesFileOperation(ConstantPath.YOURCARTPAGE);
    }

    public static YourCartPage getPageInstance() {
        if (yourCartPage == null)
            yourCartPage = new YourCartPage();
        return yourCartPage;
    }

    public ArrayList<String> getListOfProductsAlreadyAddedInCart() {
        ArrayList<String> listOfProductsAdded = new ArrayList<>();
        listOfProductsAdded.add(getElementText(properties.getValue("yourCartPageItemSauceLabsBackPack")));
        listOfProductsAdded.add(getElementText(properties.getValue("yourCartPageItemSauceLabsBikeLight")));
        listOfProductsAdded.add(getElementText(properties.getValue("yourCartPageItemSauceLabsBoltTShirt")));

        return listOfProductsAdded;
    }

    public Double getAmountTotalOfYourCart() {

        String backPackPrice = getElementText(properties.getValue("yourCartPageItemSauceLabsBackPackPrice"));
        double backPackPriceWithoutDollarSign = Double.parseDouble(backPackPrice.replace("$", ""));
        String bikeLightPrice = getElementText(properties.getValue("yourCartPageItemSauceLabsBikeLightPrice"));

        double bikeLightPriceWithoutDollarSign = Double.parseDouble(bikeLightPrice.replace("$", ""));
        String boltTShirtPrice = getElementText(properties.getValue("yourCartPageItemSauceLabsBoltTShirtPrice"));
        double boltTShirtPriceWithoutDollarSign = Double.parseDouble(boltTShirtPrice.replace("$", ""));

        return backPackPriceWithoutDollarSign+bikeLightPriceWithoutDollarSign+boltTShirtPriceWithoutDollarSign;
    }

    public void clickOnCheckoutButon() {
        clickOnElement(properties.getValue("yourCartPageCheckoutButton"));
    }

    public void tearDown() {
        PredefinedActions.driver.quit();
    }
}