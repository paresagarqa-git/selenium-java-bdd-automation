package com.sagar.automation.context;

import com.sagar.automation.driver.DriverFactory;
import com.sagar.automation.pages.LoginPage;
import com.sagar.automation.pages.SecureAreaPage;
import org.openqa.selenium.WebDriver;

/**
 * One instance per scenario, injected by cucumber-picocontainer into every step class
 * that asks for it in its constructor. Page objects are created lazily and reused for
 * the rest of the scenario, so all step classes share the same instances.
 */
public class TestContext {

    private LoginPage loginPage;
    private SecureAreaPage secureAreaPage;

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage(getDriver());
        }
        return loginPage;
    }

    public SecureAreaPage secureAreaPage() {
        if (secureAreaPage == null) {
            secureAreaPage = new SecureAreaPage(getDriver());
        }
        return secureAreaPage;
    }
}
