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

    /**
     * Runs scenarios in parallel. The number of threads comes from the "threads" Maven property
     * (see pom.xml), e.g. mvn test -Dthreads=1 for a sequential run.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
