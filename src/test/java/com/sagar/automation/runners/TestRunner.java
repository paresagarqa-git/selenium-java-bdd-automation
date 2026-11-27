package com.sagar.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.sagar.automation.stepdefinitions", "com.sagar.automation.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "rerun:target/cucumber-reports/rerun.txt"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /** Set parallel = true to run scenarios in parallel (DriverFactory is ThreadLocal-safe). */
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
