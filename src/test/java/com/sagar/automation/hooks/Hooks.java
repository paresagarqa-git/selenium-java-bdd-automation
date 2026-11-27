package com.sagar.automation.hooks;

import com.sagar.automation.driver.DriverFactory;
import com.sagar.automation.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.nio.file.Path;

public class Hooks {

    @Before(order = 0)
    public void startBrowser(Scenario scenario) {
        DriverFactory.initDriver();
        System.out.printf("[%s] %s | %s%n", Thread.currentThread().getName(),
                DriverFactory.browserName(), scenario.getName());
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = ScreenshotUtil.capture(DriverFactory.getDriver());
                // Embedded in the Cucumber HTML report...
                scenario.attach(png, "image/png", scenario.getName());
                // ...and saved as a file for the CI artifact.
                Path file = ScreenshotUtil.save(png, scenario.getName());
                scenario.log("Screenshot saved to " + file);
            }
        } catch (Exception e) {
            scenario.log("Could not capture screenshot: " + e.getMessage());
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
