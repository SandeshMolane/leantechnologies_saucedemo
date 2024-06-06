package com.leantechnologies.saucedemo.pagesandservices.pages;

import org.apache.log4j.Logger;
import com.leantechnologies.saucedemo.pagesandservices.base.PredefinedActions;
import com.leantechnologies.saucedemo.pagesandservices.constant.ConstantPath;
import com.leantechnologies.saucedemo.pagesandservices.utils.PropertiesFileOperation;

public class LoginPage extends PredefinedActions {

    private Logger log = Logger.getLogger(LoginPage.class);
    private static LoginPage loginPage;
    private PropertiesFileOperation properties;

    private LoginPage() {
        properties = new PropertiesFileOperation(ConstantPath.LOGINPAGE);
    }

    public static LoginPage getPageInstance() {
        if (loginPage == null)
            loginPage = new LoginPage();
        return loginPage;
    }

    public void enterUserName(String userName) {
        log.debug("STEP - Enter username");
        enterText(properties.getValue("loginPageUserName"), userName);
    }

    public void enterPassword(String password) {
        log.debug("STEP - Enter password");
        enterText(properties.getValue("loginPagePassword"), password);
    }

    public void clickOnSignIn() {
        clickOnElement(properties.getValue("loginPageSignInButton"));
    }

    public void login(String userName, String password) {
        enterUserName(userName);
        enterPassword(password);
        clickOnSignIn();
    }

    public String getTitleOfpage() {
        return getCurrentPageTitle();
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(properties.getValue("loginPageLogo"));
    }

    public void tearDown() {
        PredefinedActions.driver.quit();
    }
}
