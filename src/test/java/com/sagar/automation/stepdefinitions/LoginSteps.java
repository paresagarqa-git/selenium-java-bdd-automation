package com.sagar.automation.stepdefinitions;

import com.sagar.automation.context.TestContext;
import com.sagar.automation.data.TestDataReader;
import com.sagar.automation.data.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        context.loginPage().open();
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWith(String username, String password) {
        context.loginPage().login(username, password);
    }

    /** Credentials come from testdata/users.json, looked up by key. */
    @When("I log in as the {string} user")
    public void iLogInAsTheUser(String userKey) {
        User user = TestDataReader.user(userKey);
        context.loginPage().login(user.username(), user.password());
    }

    @When("I log out")
    public void iLogOut() {
        context.secureAreaPage().logout();
    }

    @Then("I should land on the secure area")
    public void iShouldLandOnTheSecureArea() {
        assertEquals(context.secureAreaPage().getHeading(), "Secure Area");
        assertTrue(context.secureAreaPage().getCurrentUrl().endsWith("/secure"),
                "Expected /secure URL but was " + context.secureAreaPage().getCurrentUrl());
    }

    @Then("I should be on the login page")
    public void iShouldBeOnTheLoginPage() {
        assertTrue(context.loginPage().getCurrentUrl().endsWith("/login"),
                "Expected /login URL but was " + context.loginPage().getCurrentUrl());
    }

    @Then("I should see the flash message {string}")
    public void iShouldSeeTheFlashMessage(String expected) {
        assertEquals(context.loginPage().getFlashMessage(), expected);
    }
}
