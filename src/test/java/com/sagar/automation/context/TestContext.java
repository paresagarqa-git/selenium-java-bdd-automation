package com.sagar.automation.context;

import com.sagar.automation.driver.DriverFactory;
import com.sagar.automation.pages.CheckboxesPage;
import com.sagar.automation.pages.DropdownPage;
import com.sagar.automation.pages.DynamicLoadingPage;
import com.sagar.automation.pages.JavaScriptAlertsPage;
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
    private CheckboxesPage checkboxesPage;
    private DropdownPage dropdownPage;
    private DynamicLoadingPage dynamicLoadingPage;
    private JavaScriptAlertsPage javaScriptAlertsPage;

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

    public CheckboxesPage checkboxesPage() {
        if (checkboxesPage == null) {
            checkboxesPage = new CheckboxesPage(getDriver());
        }
        return checkboxesPage;
    }

    public DropdownPage dropdownPage() {
        if (dropdownPage == null) {
            dropdownPage = new DropdownPage(getDriver());
        }
        return dropdownPage;
    }

    public DynamicLoadingPage dynamicLoadingPage() {
        if (dynamicLoadingPage == null) {
            dynamicLoadingPage = new DynamicLoadingPage(getDriver());
        }
        return dynamicLoadingPage;
    }

    public JavaScriptAlertsPage javaScriptAlertsPage() {
        if (javaScriptAlertsPage == null) {
            javaScriptAlertsPage = new JavaScriptAlertsPage(getDriver());
        }
        return javaScriptAlertsPage;
    }
}
