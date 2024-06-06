package com.leantechnologies.saucedemo.pagesandservices.pages;

import java.util.ArrayList;
import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;

public class ProductsPage extends PredefinedActions {

    private static ProductsPage productsPage;
    private PropertiesFileOperation properties;

    private ProductsPage() {
        properties = new PropertiesFileOperation(ConstantPath.PRODUCTSPAGE);
    }

    public static ProductsPage getPageInstance() {
        if (productsPage == null)
            productsPage = new ProductsPage();
        return productsPage;
    }

    public boolean isProductsPageDisplayed() {
        if(getElementText(properties.getValue("productsPageDisplayedXPath")).equals("Products"))
            return true;
        else
            return false;
    }

    public String verifyProductsAddedInCart() {
        return getElementText(properties.getValue("productsPageItemsAddedInCart"));
    }

    public String verifyYourCartPage() {
        return getElementText(properties.getValue("productsPageYourCartXPath"));
    }

    public ArrayList<String> getListOfProductsToBeAddedInCart() {
        ArrayList<String> listOfProductsAdded = new ArrayList<>();
        listOfProductsAdded.add(getElementText(properties.getValue("productsPageItemSauceLabsBackPack")));
        listOfProductsAdded.add(getElementText(properties.getValue("productsPageItemSauceLabsBikeLight")));
        listOfProductsAdded.add(getElementText(properties.getValue("productsPageItemSauceLabsBoltTShirt")));

        return listOfProductsAdded;
    }

    public Double getAmountTotalOfProductsToBeAddedInCart() {
        String backPackPrice = getElementText(properties.getValue("productsPageItemSauceLabsBackPackPrice"));
        double backPackPriceWithoutDollarSign = Double.parseDouble(backPackPrice.replace("$", ""));
        String bikeLightPrice = getElementText(properties.getValue("productsPageItemSauceLabsBikeLightPrice"));
        double bikeLightPriceWithoutDollarSign = Double.parseDouble(bikeLightPrice.replace("$", ""));
        String boltTShirtPrice = getElementText(properties.getValue("productsPageItemSauceLabsBoltTShirtPrice"));
        double boltTShirtPriceWithoutDollarSign = Double.parseDouble(boltTShirtPrice.replace("$", ""));

        return backPackPriceWithoutDollarSign+bikeLightPriceWithoutDollarSign+boltTShirtPriceWithoutDollarSign;
    }

    public void addProductsToCart(String... productNames) {
        for (String productName : productNames) {
            switch (productName.toLowerCase()) {
                case "backpack":
                    clickOnElement(properties.getValue("productsPageAddToCart1"));
                    break;
                case "bike light":
                    clickOnElement(properties.getValue("productsPageAddToCart2"));
                    break;
                case "bolt t-shirt":
                    clickOnElement(properties.getValue("productsPageAddToCart3"));
                    break;
                // Add more products as needed
                default:
                    throw new IllegalArgumentException("Invalid product name: " + productName);
            }
        }
    }

    public void clickOnCartIcon() {
        clickOnElement(properties.getValue("productsPageCart"));
    }

    public void tearDown() {
        PredefinedActions.driver.quit();
    }
}