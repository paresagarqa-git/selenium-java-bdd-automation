package com.sagar.automation.context;

import com.sagar.automation.driver.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 * One instance per scenario, injected by cucumber-picocontainer into every step class
 * that asks for it in its constructor. Page objects are created lazily and reused for
 * the rest of the scenario, so all step classes share the same instances.
 */
public class TestContext {

    public WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}
